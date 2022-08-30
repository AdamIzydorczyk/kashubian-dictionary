package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchExample(
    val id: Long,
    val example: String?,
    val note: String?,
    val meaning: SearchMeaning
)
