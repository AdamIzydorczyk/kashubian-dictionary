package tk.aizydorczyk.kashubian.crud.model.graphql


data class PhrasalVerbGraphQL(
    val id: Long?,
    val note: String?,
    val phrasalVerb: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhrasalVerbGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}