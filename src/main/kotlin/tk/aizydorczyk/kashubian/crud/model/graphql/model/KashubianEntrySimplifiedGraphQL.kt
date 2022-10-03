package tk.aizydorczyk.kashubian.crud.model.graphql.model


data class KashubianEntrySimplifiedGraphQL(
    var id: Long,
    var word: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KashubianEntrySimplifiedGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}