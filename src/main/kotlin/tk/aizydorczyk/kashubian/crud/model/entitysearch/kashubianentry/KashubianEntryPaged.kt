package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

data class KashubianEntryPaged(val pages: Long, val total: Long, val select: List<SearchKashubianEntry>)
