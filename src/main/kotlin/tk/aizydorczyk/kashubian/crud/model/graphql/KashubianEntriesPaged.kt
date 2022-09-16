package tk.aizydorczyk.kashubian.crud.model.graphql

data class KashubianEntriesPaged(val pages: Int, val total: Int, val select: List<KashubianEntryGraphQL>)
