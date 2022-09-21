package tk.aizydorczyk.kashubian.crud.model.graphql


data class QuoteGraphQL(
    val id: Long?,
    val note: String?,
    val quote: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuoteGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}