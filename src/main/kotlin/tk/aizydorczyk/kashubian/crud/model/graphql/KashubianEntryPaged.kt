package tk.aizydorczyk.kashubian.crud.model.graphql

data class KashubianEntryPaged(val pages: Int, val total: Int, val select: List<KashubianEntryGraphQL>)
