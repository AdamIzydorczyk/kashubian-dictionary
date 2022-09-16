package tk.aizydorczyk.kashubian.crud.query.graphql.base

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.Field
import org.jooq.QueryPart
import org.jooq.SortField
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.graphql.CriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.JoinTableWithCondition
import java.io.Serializable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

abstract class AllFinderBase : FinderBase() {
    protected fun prepareWheresWithJoins(prefix: String, where: CriteriaExpression?,
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
        fieldPath.endsWith(".LIKE_") -> field.likeIgnoreCase("%$instance%")
        fieldPath.endsWith(".LIKE") -> field.like("%$instance%")
        fieldPath.endsWith(".BY_NORMALIZED") -> field.like("%${instance.toString().normalize()}%")
        else -> null
    }

    protected fun orderByColumns(selectedFields: List<SelectedField>,
        fieldToColumnRelations: Map<String, Field<out Serializable>>): List<SortField<*>> =
        selectedFields.filter { it.arguments.isNotEmpty() }.mapNotNull {
            when (it.arguments["orderBy"]) {
                "ASC" -> fieldToColumnRelations[it.fullyQualifiedName]?.asc()
                else -> fieldToColumnRelations[it.fullyQualifiedName]?.desc()
            }
        }

    protected fun isContainsPaginationFields(fields: List<SelectedField>,
        totalFieldName: String,
        pagesFieldName: String) =
        fields.any {
            it.fullyQualifiedName == totalFieldName
                    || it.fullyQualifiedName == pagesFieldName
        }

    private val listOfTypesToFetch =
        listOf(String::class, Boolean::class, Long::class).map { it.createType(nullable = true) }

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
}