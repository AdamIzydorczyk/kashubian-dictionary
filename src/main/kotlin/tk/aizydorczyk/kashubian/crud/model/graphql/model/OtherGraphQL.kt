package tk.aizydorczyk.kashubian.crud.model.graphql.model


data class OtherGraphQL(
    val id: Long,
    val note: String?,
    var other: KashubianEntrySimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OtherGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}