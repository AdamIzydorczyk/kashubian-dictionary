package tk.aizydorczyk.kashubian.crud.model.entitysearch

data class SearchTranslation(
    val id: Long,
    val polish: String?,
    val normalizedPolish: String?,
    val english: String?,
    val normalizedEnglish: String?,
    val german: String?,
    val normalizedGerman: String?,
    val ukrainian: String?,
    val normalizedUkrainian: String?,
    val meaning: SearchMeaning
)