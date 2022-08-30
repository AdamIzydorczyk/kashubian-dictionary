package tk.aizydorczyk.kashubian.crud.model.entitysearch


data class SearchAntonym(
    val id: Long,
    val note: String?,
    var antonym: SearchMeaning,
    val meaning: SearchMeaning
)