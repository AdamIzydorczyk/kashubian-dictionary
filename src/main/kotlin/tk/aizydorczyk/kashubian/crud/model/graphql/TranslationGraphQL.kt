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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TranslationGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}