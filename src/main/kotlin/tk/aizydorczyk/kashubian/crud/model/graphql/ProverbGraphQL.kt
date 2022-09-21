package tk.aizydorczyk.kashubian.crud.model.graphql

data class ProverbGraphQL(
    val id: Long?,
    val note: String?,
    val proverb: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProverbGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

