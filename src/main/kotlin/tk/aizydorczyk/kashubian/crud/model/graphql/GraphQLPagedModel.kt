package tk.aizydorczyk.kashubian.crud.model.graphql

data class GraphQLPagedModel<out GraphQLModel>(val pages: Int, val total: Int, val select: List<GraphQLModel>)
