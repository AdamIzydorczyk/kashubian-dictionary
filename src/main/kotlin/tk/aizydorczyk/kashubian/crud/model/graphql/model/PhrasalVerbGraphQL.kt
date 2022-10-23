package tk.aizydorczyk.kashubian.crud.model.graphql.model


data class PhrasalVerbGraphQL(
    val id: Long,
    val note: String?,
    val idiom: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhrasalVerbGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}