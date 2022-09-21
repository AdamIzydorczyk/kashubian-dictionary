package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class MeaningsCriteriaExpressionSimplified(
    val id: NumericCriteria?,
    val definition: StringCriteria?,
    val kashubianEntry: KashubianEntryCriteriaExpressionSimplified?)