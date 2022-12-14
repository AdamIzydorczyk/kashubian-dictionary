package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.graphql.model.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.page.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningGraphQLMapper
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningTable
import tk.aizydorczyk.kashubian.crud.query.graphql.base.AllFinderBase
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.MeaningQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS

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
