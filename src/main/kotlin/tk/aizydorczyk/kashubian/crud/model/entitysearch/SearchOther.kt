package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchOther(
    val id: Long,
    val note: String?,
    var other: SearchKashubianEntry,
    val kashubianEntry: SearchKashubianEntry
)
