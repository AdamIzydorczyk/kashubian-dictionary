package tk.aizydorczyk.kashubian.crud.query.graphql.entry

import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.impl.TableImpl
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.model.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.graphql.base.OneFinderBase
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.KashubianEntryQueryRelations.FIND_ONE_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.KashubianEntryQueryRelations.FIND_ONE_FIELD_TO_JOIN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.KashubianEntryQueryRelations.entryId
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.KashubianEntryQueryRelations.entryTable

@Component
class OneKashubianEntryFinder(override val dsl: DSLContext) :
    OneFinderBase<KashubianEntryGraphQL>(dsl, KashubianEntryGraphQLMapper()) {

    fun findOneKashubianEntry(selectedFields: List<SelectedField>, id: Long): KashubianEntryGraphQL? =
        findOne(selectedFields, id)

    override fun idField(): Field<Long> = entryTable().ID
    override fun idFieldWithAlias(): Field<Long> = entryId()
    override fun table(): TableImpl<*> = entryTable()
    override fun fieldToJoinRelations() = FIND_ONE_FIELD_TO_JOIN_RELATIONS
    override fun fieldToColumnRelations(): Map<String, Field<*>> =
        FIND_ONE_FIELD_TO_COLUMN_RELATIONS

}