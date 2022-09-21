package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class SoundFileCriteriaExpression(
    val id: NumericCriteria?,
    val type: StringCriteria?,
    val fileName: StringCriteria?)