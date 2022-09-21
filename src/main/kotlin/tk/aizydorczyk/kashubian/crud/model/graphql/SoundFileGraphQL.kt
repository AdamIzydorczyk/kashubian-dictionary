package tk.aizydorczyk.kashubian.crud.model.graphql

data class SoundFileGraphQL(
    val id: Long?,
    val type: String?,
    val fileName: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SoundFileGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}