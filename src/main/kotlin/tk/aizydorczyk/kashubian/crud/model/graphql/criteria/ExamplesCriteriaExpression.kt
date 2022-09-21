package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class ExamplesCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val example: StringCriteria?)