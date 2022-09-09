package tk.aizydorczyk.kashubian.crud.model.entitysearch

import tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry.SearchKashubianEntry

data class SearchSoundFile(
    val id: Long,
    val fileName: String,
    val type: String,
    val kashubianEntry: SearchKashubianEntry
)
