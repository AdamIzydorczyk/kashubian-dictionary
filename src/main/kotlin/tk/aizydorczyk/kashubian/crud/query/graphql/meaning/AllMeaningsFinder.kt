package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.graphql.base.AllFinderBase
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningId
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.meaningTable

@Component
class AllMeaningsFinder(override val dsl: DSLContext) :
    AllFinderBase<MeaningGraphQL>(dsl, MeaningGraphQLMapper()) {
    fun findAllMeanings(where: MeaningsCriteriaExpression?,
        selectedFields: MutableList<SelectedField>,
        page: PageCriteria?): MeaningsPaged = findAll(MeaningsCriteriaExpression::class, where, selectedFields, page)
        .let(::MeaningsPaged)

    override fun idFieldWithAlias() = meaningId()

    override fun fieldToJoinRelations() = FIND_ALL_FIELD_TO_JOIN_RELATIONS

    override fun idField(): Field<Long> = table().ID

    override fun table() = meaningTable()

    override fun fieldToColumnRelations() = FIND_ALL_FIELD_TO_COLUMN_RELATIONS

    override fun relationsWithJoin() = CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN

    override fun pageTypeName() = MeaningsPaged::class.simpleName!!
}
