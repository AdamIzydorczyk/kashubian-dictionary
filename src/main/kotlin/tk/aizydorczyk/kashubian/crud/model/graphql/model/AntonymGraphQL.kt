package tk.aizydorczyk.kashubian.crud.model.graphql.model

data class AntonymGraphQL(
    val id: Long?,
    val note: String?,
    var antonym: MeaningSimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AntonymGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}