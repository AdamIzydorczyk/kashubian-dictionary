package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.SelectFieldOrAsterisk
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper

@Component
class OneKashubianEntryFinder(private val dsl: DSLContext) : OneFinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = KashubianEntryGraphQLMapper()

    fun findOne(selectedFields: MutableList<SelectedField>,
        id: Long): KashubianEntryGraphQL? {
        val selectedColumns: MutableList<SelectFieldOrAsterisk?> =
            selectColumns(selectedFields, KashubianEntryQueryRelations.FIND_ONE_FIELD_TO_COLUMN_RELATIONS)
        selectedColumns.add(KashubianEntryQueryRelations.entryId())

        val selectedJoins = selectedFields
            .mapNotNull { KashubianEntryQueryRelations.FIND_ONE_FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        return dsl.select(selectedColumns)
            .from(KashubianEntryQueryRelations.entryTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(KashubianEntryQueryRelations.entryTable().ID.eq(id))
            .apply { logger.info("Select query: $sql") }
            .let { mapper.map(it.fetch()).firstOrNull() }
    }
}