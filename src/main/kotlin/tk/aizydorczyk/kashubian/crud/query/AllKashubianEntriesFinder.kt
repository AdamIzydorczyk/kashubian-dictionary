package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.SortField
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntriesPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper
import java.io.Serializable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

@Component
class AllKashubianEntriesFinder(private val dsl: DSLContext) : FinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = KashubianEntryGraphQLMapper()

    fun findAll(where: KashubianEntryCriteriaExpression?,
        selectedFields: MutableList<SelectedField>,
        page: PageCriteria?): KashubianEntriesPaged {
        val wheresWithJoins = prepareWheresWithJoins(where)

        val wheres = wheresWithJoins.map { it.condition() }
        val whereJoins = wheresWithJoins.map { it.joins() }
            .flatten()
            .distinct()

        val selectedColumns: MutableList<SelectFieldOrAsterisk?> = selectColumns(selectedFields,
                KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS)
        selectedColumns.add(KashubianEntryQueryRelations.entryId())

        val selectedJoins = selectedFields
            .mapNotNull { KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val entriesCount = countEntriesIfSelected(selectedFields, whereJoins, wheres)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (entriesCount + limit - 1) / limit

        val ordersBy: List<SortField<*>> = orderByColumns(selectedFields,
                KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS)

        return dsl.select(selectedColumns)
            .from(KashubianEntryQueryRelations.entryTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(KashubianEntryQueryRelations.entryTable().ID.`in`(
                    DSL.select(KashubianEntryQueryRelations.entryTable().ID)
                        .from(KashubianEntryQueryRelations.entryTable())
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
            .orderBy(ordersBy)
            .apply { logger.info("Select query: $sql") }
            .let { KashubianEntriesPaged(pageCount, entriesCount, mapper.map(it.fetch())) }
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
                val field =
                    KashubianEntryQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN[fieldPath]!!.first as Field<Any>
                val condition = prepareCondition(fieldPath, field, instance)

                val joins = KashubianEntryQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN[fieldPath]!!.second

                Pair(condition, joins)
            }

    private fun prepareCondition(fieldPath: String,
        field: Field<Any>,
        instance: Any) = when {
        fieldPath.endsWith(".EQ") -> field.eq(instance)
        fieldPath.endsWith(".LIKE_") -> field.likeIgnoreCase("%$instance%")
        fieldPath.endsWith(".LIKE") -> field.like("%$instance%")
        fieldPath.endsWith(".BY_NORMALIZED") -> field.like("%${instance.toString().normalize()}%")
        else -> null
    }

    private fun countEntriesIfSelected(
        selectedFields: MutableList<SelectedField>,
        whereJoins: List<KashubianEntryQueryRelations.JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields)) {
            true -> dsl.select(DSL.count())
                .from(Tables.KASHUBIAN_ENTRY.`as`("entry"))
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

    private fun orderByColumns(selectedFields: MutableList<SelectedField>,
        fieldToColumnRelations: Map<String, Field<out Serializable>>): List<SortField<*>> =
        selectedFields.filter { it.arguments.isNotEmpty() }.mapNotNull {
            when (it.arguments["orderBy"]) {
                "ASC" -> fieldToColumnRelations[it.fullyQualifiedName]?.asc()
                else -> fieldToColumnRelations[it.fullyQualifiedName]?.desc()
            }
        }

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any {
            it.fullyQualifiedName == "KashubianEntryPaged.total"
                    || it.fullyQualifiedName == "KashubianEntryPaged.pages"
        }

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
}