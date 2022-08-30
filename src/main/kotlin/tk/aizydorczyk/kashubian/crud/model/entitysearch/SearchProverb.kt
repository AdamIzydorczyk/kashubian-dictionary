package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchProverb(
    val id: Long,
    val proverb: String,
    val note: String,
    val meaning: SearchMeaning
)
