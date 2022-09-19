package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.entitysearch.tables.Meaning
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.graphql.base.OneFinderBase
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ONE_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ONE_FIELD_TO_JOIN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningId
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningTable

@Component
class OneMeaningFinder(override val dsl: DSLContext)
    : OneFinderBase<MeaningGraphQL>(dsl, MeaningGraphQLMapper()) {
    fun findOneMeaning(selectedFields: List<SelectedField>, id: Long): MeaningGraphQL? = findOne(selectedFields, id)
    override fun idField(): Field<Long> = meaningTable().ID
    override fun idFieldWithAlias(): Field<Long> = meaningId()
    override fun table(): Meaning = meaningTable()
    override fun fieldToJoinRelations() = FIND_ONE_FIELD_TO_JOIN_RELATIONS
    override fun fieldToColumnRelations(): Map<String, Field<*>> = FIND_ONE_FIELD_TO_COLUMN_RELATIONS
}