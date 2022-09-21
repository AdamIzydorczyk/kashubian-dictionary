package tk.aizydorczyk.kashubian.crud.model.graphql

data class SynonymGraphQL(
    val id: Long?,
    val note: String?,
    var synonym: MeaningSimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SynonymGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}