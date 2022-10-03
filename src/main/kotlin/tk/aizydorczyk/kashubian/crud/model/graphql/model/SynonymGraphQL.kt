package tk.aizydorczyk.kashubian.crud.model.graphql.model

data class SynonymGraphQL(
    val id: Long,
    val note: String?,
    var synonym: MeaningSimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SynonymGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}