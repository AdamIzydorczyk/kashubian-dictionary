package tk.aizydorczyk.kashubian.crud.query.graphql.base

data class GraphQLPagedModel<out GraphQLModel>(val pages: Int, val total: Int, val select: List<GraphQLModel>)
