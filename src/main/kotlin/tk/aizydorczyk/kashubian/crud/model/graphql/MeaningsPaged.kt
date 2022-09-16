package tk.aizydorczyk.kashubian.crud.model.graphql

data class MeaningsPaged(val pages: Int, val total: Int, val select: List<MeaningGraphQL>)
