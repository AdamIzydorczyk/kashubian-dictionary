package tk.aizydorczyk.kashubian.crud.model.graphql.model


data class ExampleGraphQL(
    val id: Long,
    val note: String?,
    val example: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExampleGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}