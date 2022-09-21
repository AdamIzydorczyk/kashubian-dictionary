package tk.aizydorczyk.kashubian.crud.model.graphql.model


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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TranslationGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}