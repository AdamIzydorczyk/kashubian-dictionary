package tk.aizydorczyk.kashubian.crud.model.graphql


data class OtherGraphQL(
    val id: Long?,
    val note: String?,
    var other: KashubianEntrySimplifiedGraphQL? = null
)