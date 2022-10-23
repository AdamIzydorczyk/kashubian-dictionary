package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class IdiomsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val idiom: StringCriteria?)