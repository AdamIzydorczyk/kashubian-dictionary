package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchSynonym(
    val id: Long,
    val note: String?,
    var synonym: SearchMeaning,
    val meaning: SearchMeaning
)
