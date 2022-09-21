package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class ProverbsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val proverb: StringCriteria?)