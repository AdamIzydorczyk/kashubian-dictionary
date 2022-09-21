package tk.aizydorczyk.kashubian.crud.model.graphql.page

import tk.aizydorczyk.kashubian.crud.model.graphql.model.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.query.graphql.base.GraphQLPagedModel

data class KashubianEntriesPaged(val pages: Int,
    val total: Int,
    val select: List<KashubianEntryGraphQL>) {
    constructor(pagedModel: GraphQLPagedModel<KashubianEntryGraphQL>) :
            this(pagedModel.pages, pagedModel.total, pagedModel.select)
}
