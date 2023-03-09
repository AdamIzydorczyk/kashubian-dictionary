package tk.aizydorczyk.kashubian.crud.query.graphql.base

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.SortField

import org.jooq.impl.DSL
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

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
                    "LENGTH_ASC" -> fieldToColumnRelations[it.fullyQualifiedName]
                            ?.let { field -> textLengthField(field) }?.asc()

                    "ASC" -> fieldToColumnRelations[it.fullyQualifiedName]?.asc()
                    else -> fieldToColumnRelations[it.fullyQualifiedName]?.desc()
                }
            }

    protected fun textLengthField(field: Field<*>?): Field<Int> {
        val unwrapped = field!!::class
                .declaredMemberFunctions
                .firstOrNull { it.name == "getAliasedField" }
                ?.apply { isAccessible = true }
                ?.call(field) as Field<String>
        return DSL.length(unwrapped)
    }

    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinTable() = this.first
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinCondition() = this.second
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.idColumn() = this.third

    fun Pair<Condition?, List<JoinTableWithCondition>>.condition() = this.first
    fun Pair<Condition?, List<JoinTableWithCondition>>.joins() = this.second

    data class FieldWithInstance(val field: KProperty1<*, *>, val instance: Any?, val fieldPath: String)
}