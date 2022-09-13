package tk.aizydorczyk.kashubian.crud.model.graphql

data class AntonymGraphQL(
    val id: Long?,
    val note: String?,
    var antonym: MeaningSimplifiedGraphQL? = null
)