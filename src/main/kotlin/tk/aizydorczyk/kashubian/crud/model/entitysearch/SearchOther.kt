package tk.aizydorczyk.kashubian.crud.model.entitysearch

import tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry.SearchKashubianEntry

data class SearchOther(
    val id: Long,
    val note: String?,
    var other: SearchKashubianEntry,
    val kashubianEntry: SearchKashubianEntry
)
