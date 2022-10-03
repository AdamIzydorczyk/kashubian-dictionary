package tk.aizydorczyk.kashubian.crud.model.graphql.model

data class SoundFileGraphQL(
    val id: Long,
    val type: String?,
    val fileName: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SoundFileGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}