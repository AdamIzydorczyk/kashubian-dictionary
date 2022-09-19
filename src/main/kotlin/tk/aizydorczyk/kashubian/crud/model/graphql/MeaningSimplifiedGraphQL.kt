package tk.aizydorczyk.kashubian.crud.model.graphql

data class MeaningSimplifiedGraphQL(
    val id: Long?,
    val definition: String?,
    var kashubianEntry: KashubianEntrySimplifiedGraphQL? = null
)