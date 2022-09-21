package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class SynonymsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val synonym: MeaningsCriteriaExpressionSimplified?)