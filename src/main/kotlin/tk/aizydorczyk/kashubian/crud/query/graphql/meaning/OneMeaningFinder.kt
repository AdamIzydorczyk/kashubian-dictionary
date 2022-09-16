package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.SelectFieldOrAsterisk
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.graphql.base.OneFinderBase
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ONE_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ONE_FIELD_TO_JOIN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningId
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningTable

@Component
class OneMeaningFinder(private val dsl: DSLContext) : OneFinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = MeaningGraphQLMapper()
    fun findOne(selectedFields: List<SelectedField>, id: Long): MeaningGraphQL? {
        val selectedColumns: MutableList<SelectFieldOrAsterisk?> =
            selectColumns(selectedFields, FIND_ONE_FIELD_TO_COLUMN_RELATIONS)
        selectedColumns.add(meaningId())

        val selectedJoins = selectedFields
            .mapNotNull { FIND_ONE_FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        return dsl.select(selectedColumns)
            .from(meaningTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(meaningTable().ID.eq(id))
            .apply { logger.info("Select query: $sql") }
            .let { mapper.map(it.fetch()).firstOrNull() }
    }
}