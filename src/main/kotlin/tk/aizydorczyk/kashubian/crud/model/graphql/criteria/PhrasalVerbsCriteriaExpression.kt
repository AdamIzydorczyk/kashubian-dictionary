package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class PhrasalVerbsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val phrasalVerb: StringCriteria?)