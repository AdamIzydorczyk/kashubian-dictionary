package tk.aizydorczyk.kashubian.crud.query.graphql.base

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.SortField
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import kotlin.reflect.KProperty1

abstract class FinderBase {
    protected fun selectColumns(selectedFields: List<SelectedField>,
        fieldToColumnRelations: Map<String, Field<*>>): MutableSet<SelectFieldOrAsterisk?> =
        selectedFields
            .mapNotNull { fieldToColumnRelations[it.fullyQualifiedName] }
            .toMutableSet()

    protected fun orderByColumns(selectedFields: List<SelectedField>,
        fieldToColumnRelations: Map<String, Field<*>>): List<SortField<*>> =
        selectedFields.filter { it.arguments.isNotEmpty() }.mapNotNull {
            when (it.arguments["orderBy"]) {
                "ASC" -> fieldToColumnRelations[it.fullyQualifiedName]?.asc()
                else -> fieldToColumnRelations[it.fullyQualifiedName]?.desc()
            }
        }

    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinTable() = this.first
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinCondition() = this.second
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.idColumn() = this.third

    fun Pair<Condition?, List<JoinTableWithCondition>>.condition() = this.first
    fun Pair<Condition?, List<JoinTableWithCondition>>.joins() = this.second

    data class FieldWithInstance(val field: KProperty1<*, *>, val instance: Any?, val fieldPath: String)
}