package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class QuotesCriteriaExpression(
    val id: NumericCriteria?,
    val note: NumericCriteria?,
    val quote: StringCriteria?)