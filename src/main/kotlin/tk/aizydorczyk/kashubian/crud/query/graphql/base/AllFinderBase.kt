package tk.aizydorczyk.kashubian.crud.query.graphql.base

import com.fasterxml.jackson.databind.JsonNode
import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SelectFieldOrAsterisk
import org.jooq.SelectSeekStepN
import org.jooq.SortField
import org.jooq.impl.DSL.condition
import org.jooq.impl.DSL.count
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.select
import org.jooq.impl.DSL.`val`
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SELECT_PREFIX
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

abstract class AllFinderBase<out GraphQLModel>(open val dsl: DSLContext, open val mapper: GraphQLMapper<GraphQLModel>) :
    FinderBase() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    protected fun findAll(criteriaExpressionClass: KClass<out CriteriaExpression>,
        where: CriteriaExpression?,
        selectedFields: MutableList<SelectedField>,
        page: PageCriteria?): GraphQLPagedModel<GraphQLModel> {
        val wheresWithJoins = prepareWheresWithJoins(
                where = where,
                declaredMemberProperties = criteriaExpressionClass.declaredMemberProperties,
                criteriaToColumnRelationsWithJoin = relationsWithJoin()
        )

        val wheres = wheresWithJoins.map { it.condition() }
        val whereJoins = wheresWithJoins.map { it.joins() }
            .flatten()
            .distinct()

        val selectedColumns: MutableSet<SelectFieldOrAsterisk?> = selectColumns(selectedFields,
                fieldToColumnRelations())
        selectedColumns.add(idFieldWithAlias())

        val selectedJoins = selectedFields
            .mapNotNull { fieldToJoinRelations()[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val ordersBy = orderByColumns(selectedFields,
                fieldToColumnRelations())

        val elementsCount = countEntriesIfPaginationFieldsExists(selectedFields, whereJoins, wheres)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (elementsCount + limit - 1) / limit

        return selectElements(selectedFields,
                selectedColumns,
                selectedJoins,
                whereJoins,
                wheres,
                pageStart,
                limit,
                ordersBy)
            ?.let { GraphQLPagedModel(pageCount, elementsCount, mapper.map(it.fetch())) }
            ?: GraphQLPagedModel(pageCount, elementsCount, emptyList())
    }

    private fun selectElements(selectedFields: MutableList<SelectedField>,
        selectedColumns: MutableSet<SelectFieldOrAsterisk?>,
        selectedJoins: List<Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>>,
        whereJoins: List<JoinTableWithCondition>,
        wheres: List<Condition?>,
        pageStart: Int,
        limit: Int,
        ordersBy: List<SortField<*>>): SelectSeekStepN<Record>? {
        return when (isContainsSelectField(selectedFields)) {
            true -> dsl.select(selectedColumns)
                .from(table())
                .apply {
                    selectedJoins.forEach {
                        leftJoin(it.joinTable()).on(it.joinCondition())
                    }
                }
                .where(idField().`in`(
                        select(field(idFieldWithAlias().name, Long::class.java))
                            .from(select(selectedColumns)
                                .from(table())
                                .apply {
                                    whereJoins.forEach {
                                        leftJoin(it.joinTable).on(it.joinCondition)
                                    }
                                    wheres.forEach {
                                        where(it)
                                    }
                                }
                                .orderBy(ordersBy)
                                .offset(pageStart)
                                .limit(limit).asTable(table()))
                )).orderBy(ordersBy)
                .apply { logger.info("Select query: $sql") }

            false -> null
        }
    }

    private fun countEntriesIfPaginationFieldsExists(
        selectedFields: MutableList<SelectedField>,
        whereJoins: List<JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields)) {
            true -> dsl.select(count())
                .from(table())
                .apply {
                    whereJoins.forEach {
                        leftJoin(it.joinTable).on(it.joinCondition)
                    }

                    wheres.forEach {
                        where(it)
                    }
                    logger.info("Count query: $sql")
                }.fetchOne(0, Int::class.java) ?: 0

            false -> 0
        }

    private fun selectEntriesIfSelectFieldExists(
        selectedFields: MutableList<SelectedField>,
        whereJoins: List<JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields)) {
            true -> dsl.select(count())
                .from(table())
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
    @Suppress("UNCHECKED_CAST")
    private fun prepareWheresWithJoins(prefix: String = SELECT_PREFIX, where: CriteriaExpression?,
        declaredMemberProperties: Collection<KProperty1<out CriteriaExpression, *>>,
        criteriaToColumnRelationsWithJoin: Map<String, Pair<QueryPart, List<JoinTableWithCondition>>>
    ): List<Pair<Condition?, List<JoinTableWithCondition>>> = declaredMemberProperties.flatMap {
        flatMapObjectFields(it, where, prefix)
    }.filter { it.instance != null }
        .map { Pair(it.field.call(it.instance), it.fieldPath) }
        .filter { it.first != null }
        .map { instanceWithField ->
            val instance = instanceWithField.first!!
            val fieldPath = instanceWithField.second
            val field =
                criteriaToColumnRelationsWithJoin[fieldPath]!!.first as Field<Any>
            val condition = prepareCondition(fieldPath, field, instance)

            val joins = criteriaToColumnRelationsWithJoin[fieldPath]!!.second

            Pair(condition, joins)
        }

    private fun prepareCondition(fieldPath: String,
        field: Field<Any>,
        instance: Any) = when {
        fieldPath.endsWith(".EQ") -> field.eq(instance)
        fieldPath.endsWith("._LIKE") -> field.likeIgnoreCase("%$instance%")
        fieldPath.endsWith(".LIKE") -> field.like("%$instance%")
        fieldPath.endsWith(".BY_NORMALIZED") -> field.like("${instance.toString().normalize()}%")
        fieldPath.endsWith(".BY_JSON") -> jsonContains(field, "$instance")
        else -> null
    }

    private fun jsonContains(field: Field<Any>, value: String): Condition {
        return condition("{0} @> {1}", field, `val`(value, field))
    }

    private fun isContainsPaginationFields(fields: List<SelectedField>) =
        fields.any {
            it.fullyQualifiedName == "${pageTypeName()}.pages"
                    || it.fullyQualifiedName == "${pageTypeName()}.total"
        }

    private fun isContainsSelectField(fields: List<SelectedField>) =
        fields.any {
            it.fullyQualifiedName == "${pageTypeName()}.select"
        }

    private val listOfTypesToFetch =
        listOf(String::class.createType(nullable = true),
                Boolean::class.createType(nullable = true),
                Long::class.createType(nullable = true),
                JsonNode::class.createType(nullable = true))

    private fun flatMapObjectFields(field: KProperty1<*, *>,
        instance: Any?,
        fieldPath: String): List<FieldWithInstance> =
        if (field.returnType in listOfTypesToFetch) {
            listOf(FieldWithInstance(field, instance, "$fieldPath.${field.name}"))
        } else {
            field.returnType.jvmErasure.declaredMemberProperties.flatMap {
                instance?.let { inst -> flatMapObjectFields(it, field.call(inst), "$fieldPath.${field.name}") }
                    ?: emptyList()
            }
        }

    protected abstract fun idFieldWithAlias(): Field<Long>

    protected abstract fun fieldToJoinRelations(): Map<String, Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>>

    protected abstract fun idField(): Field<Long>

    protected abstract fun table(): TableImpl<*>

    protected abstract fun fieldToColumnRelations(): Map<String, Field<*>>

    protected abstract fun relationsWithJoin(): Map<String, Pair<QueryPart, List<JoinTableWithCondition>>>

    protected abstract fun pageTypeName(): String
}