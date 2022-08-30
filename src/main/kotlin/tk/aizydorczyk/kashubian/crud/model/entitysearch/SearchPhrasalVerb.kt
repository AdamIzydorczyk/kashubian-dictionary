package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchPhrasalVerb(
    val id: Long,
    val phrasalVerb: String,
    val note: String,
    val meaning: SearchMeaning
)
