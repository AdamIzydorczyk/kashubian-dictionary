package tk.aizydorczyk.kashubian.crud.model.graphql


data class TranslationGraphQL(
    val id: Long?,
    val polish: String?,
    val normalizedPolish: String?,
    val english: String?,
    val normalizedEnglish: String?,
    val ukrainian: String?,
    val normalizedUkrainian: String?,
    val german: String?,
    val normalizedGerman: String?,
)