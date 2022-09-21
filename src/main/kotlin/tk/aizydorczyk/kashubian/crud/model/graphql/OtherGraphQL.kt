package tk.aizydorczyk.kashubian.crud.model.graphql


data class OtherGraphQL(
    val id: Long?,
    val note: String?,
    var other: KashubianEntrySimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OtherGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}