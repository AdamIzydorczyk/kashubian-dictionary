package tk.aizydorczyk.kashubian.crud.query.graphql.base

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class OneFinderBase<out GraphQLModel>(open val dsl: DSLContext, open val mapper: GraphQLMapper<GraphQLModel>) :
    FinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    protected fun findOne(selectedFields: List<SelectedField>, id: Long): GraphQLModel? {
        val selectedColumns: MutableSet<SelectFieldOrAsterisk?> =
            selectColumns(selectedFields, fieldToColumnRelations())
        selectedColumns.add(idFieldWithAlias())

        val selectedJoins = selectedFields
            .mapNotNull { fieldToJoinRelations()[it.fullyQualifiedName] }

        val ordersBy = orderByColumns(selectedFields,
                fieldToColumnRelations())

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        return dsl.select(selectedColumns)
            .from(table())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(idField().eq(id))
            .orderBy(ordersBy)
            .apply { logger.info("Select query: $sql") }
            .let { mapper.map(it.fetch()).firstOrNull() }
    }

    protected abstract fun idField(): Field<Long>

    protected abstract fun idFieldWithAlias(): Field<Long>

    protected abstract fun table(): TableImpl<*>

    protected abstract fun fieldToJoinRelations(): Map<String, Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>>
    protected abstract fun fieldToColumnRelations(): Map<String, Field<*>>
}