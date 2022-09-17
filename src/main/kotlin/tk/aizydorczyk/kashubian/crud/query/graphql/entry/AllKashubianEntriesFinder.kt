package tk.aizydorczyk.kashubian.crud.query.graphql.entry

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntriesPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.graphql.base.AllFinderBase

@Component
class AllKashubianEntriesFinder(override val dsl: DSLContext) :
    AllFinderBase<KashubianEntryGraphQL>(dsl, KashubianEntryGraphQLMapper()) {
    fun findAllKashubianEntries(where: KashubianEntryCriteriaExpression?,
        selectedFields: MutableList<SelectedField>,
        page: PageCriteria?): KashubianEntriesPaged =
        findAll(KashubianEntryCriteriaExpression::class, where, selectedFields, page)
            .let(::KashubianEntriesPaged)

    override fun idFieldWithAlias() = KashubianEntryQueryRelations.entryId()

    override fun fieldToJoinRelations() = KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS

    override fun idField(): Field<Long> =
        table().ID

    override fun table() = KashubianEntryQueryRelations.entryTable()

    override fun fieldToColumnRelations() = KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS

    override fun relationsWithJoin() = KashubianEntryQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN

    override fun pageTypeName() = KashubianEntriesPaged::class.simpleName!!
}