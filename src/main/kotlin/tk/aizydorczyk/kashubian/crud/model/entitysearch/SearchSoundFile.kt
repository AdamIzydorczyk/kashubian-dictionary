package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchSoundFile(
    val id: Long,
    val fileName: String,
    val type: String,
    val kashubianEntry: SearchKashubianEntry
)
