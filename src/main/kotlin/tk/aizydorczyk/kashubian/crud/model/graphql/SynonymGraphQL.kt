package tk.aizydorczyk.kashubian.crud.model.graphql

data class SynonymGraphQL(
    val id: Long?,
    val note: String?,
    var synonym: MeaningSimplifiedGraphQL? = null
)