package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.DataFetchingEnvironment
import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.impl.DSL.count
import org.jooq.impl.DSL.select
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.KASHUBIAN_ENTRY
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.FIELD_TO_JOIN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.entryId
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.entryTable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

@Controller
class KashubianEntryQuery(
    private val dsl: DSLContext) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = KashubianEntryGraphQLMapper()

    @QueryMapping
    fun findAllSearchKashubianEntries(
        @Argument("page") page: PageCriteria?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntryPaged {
        val selectedFields = env.selectionSet.fields
        val wheresWithJoins = prepareWheresWithJoins(where)

        val wheres = wheresWithJoins.map { it.condition() }
        val whereJoins = wheresWithJoins.map { it.joins() }
            .flatten()
            .distinct()

        val selectedColumns: MutableList<SelectFieldOrAsterisk?> = selectFields(env)
        selectedColumns.add(entryId())

        val selectedJoins = selectedFields
            .mapNotNull { FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val entriesCount = countEntriesIfSelected(selectedFields, whereJoins, wheres)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (entriesCount + limit - 1) / limit

        return dsl.select(selectedColumns)
            .from(entryTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(entryTable().ID.`in`(
                    select(entryTable().ID)
                        .from(entryTable())
                        .apply {
                            whereJoins.forEach {
                                leftJoin(it.joinTable).on(it.joinCondition)
                            }
                            wheres.forEach {
                                where(it)
                            }
                        }
                        .offset(pageStart)
                        .limit(limit)))
            .orderBy(orderByColumns(selectedFields))
            .apply { logger.info("Count query: $sql") }
            .let { KashubianEntryPaged(pageCount, entriesCount, mapper.map(it.fetch())) }
    }

    private fun prepareWheresWithJoins(where: KashubianEntryCriteriaExpression?) =
        KashubianEntryCriteriaExpression::class.declaredMemberProperties.flatMap {
            flatMapObjectFields(it, where, "entry")
        }.filter { it.instance != null }
            .map { Pair(it.field.call(it.instance), it.fieldPath) }
            .filter { it.first != null }
            .map { instanceWithField ->
                val instance = instanceWithField.first!!
                val fieldPath = instanceWithField.second
                val field = CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN[fieldPath]!!.first as Field<Any>
                val condition = prepareCondition(fieldPath, field, instance)

                val joins = CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN[fieldPath]!!.second

                Pair(condition, joins)
            }

    private fun prepareCondition(fieldPath: String,
        field: Field<Any>,
        instance: Any) = when {
        fieldPath.endsWith(".EQ") -> field.eq(instance)
        fieldPath.endsWith(".LIKE_") -> field.likeIgnoreCase("%$instance%")
        fieldPath.endsWith(".LIKE") -> field.like("%$instance%")
        else -> null
    }

    private fun countEntriesIfSelected(
        selectedFields: MutableList<SelectedField>,
        whereJoins: List<KashubianEntryQueryRelations.JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields)) {
            true -> dsl.select(count())
                .from(KASHUBIAN_ENTRY.`as`("entry"))
                .apply {
                    whereJoins.forEach {
                        leftJoin(it.joinTable).on(it.joinCondition)
                    }

                    wheres.forEach {
                        where(it)
                    }
                    logger.info("Select query: $sql")
                }.fetchOne(0, Int::class.java) ?: 0
            false -> 0
        }

    private fun orderByColumns(selectedFields: MutableList<SelectedField>) =
        selectedFields.filter { it.arguments.isNotEmpty() }.mapNotNull {
            when (it.arguments["orderBy"]) {
                "ASC" -> FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName]?.asc()
                else -> FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName]?.desc()
            }
        }

    private fun selectFields(env: DataFetchingEnvironment): MutableList<SelectFieldOrAsterisk?> =
        env.selectionSet.fields
            .mapNotNull { FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName] }
            .toMutableList()

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any {
            it.fullyQualifiedName == "KashubianEntryPaged.total"
                    || it.fullyQualifiedName == "KashubianEntryPaged.pages"
        }

    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinTable() = this.first
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinCondition() = this.second
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.idColumn() = this.third

    fun Pair<Condition?, List<KashubianEntryQueryRelations.JoinTableWithCondition>>.condition() = this.first
    fun Pair<Condition?, List<KashubianEntryQueryRelations.JoinTableWithCondition>>.joins() = this.second

    val listOfTypesToFetch = listOf(String::class, Boolean::class, Long::class).map { it.createType(nullable = true) }

    fun flatMapObjectFields(field: KProperty1<*, *>, instance: Any?, fieldPath: String): List<FieldWithInstance> =
        if (field.returnType in listOfTypesToFetch) {
            listOf(FieldWithInstance(field, instance, "$fieldPath.${field.name}"))
        } else {
            field.returnType.jvmErasure.declaredMemberProperties.flatMap {
                instance?.let { inst -> flatMapObjectFields(it, field.call(inst), "$fieldPath.${field.name}") }
                    ?: emptyList()
            }
        }

    data class FieldWithInstance(val field: KProperty1<*, *>, val instance: Any?, val fieldPath: String)

}