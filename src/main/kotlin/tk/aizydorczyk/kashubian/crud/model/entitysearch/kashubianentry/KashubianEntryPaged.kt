package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

data class KashubianEntryPaged(val pages: Int, val total: Int, val select: List<KashubianEntryGraphQL>)
