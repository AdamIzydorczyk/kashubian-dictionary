package tk.aizydorczyk.kashubian.crud.model.graphql

data class MeaningSimplifiedGraphQL(
    val id: Long?,
    val definition: String?,
    var kashubianEntry: KashubianEntrySimplifiedGraphQL? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeaningSimplifiedGraphQL

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}