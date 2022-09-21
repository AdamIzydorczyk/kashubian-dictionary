package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class AntonymsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val antonym: MeaningsCriteriaExpressionSimplified?)