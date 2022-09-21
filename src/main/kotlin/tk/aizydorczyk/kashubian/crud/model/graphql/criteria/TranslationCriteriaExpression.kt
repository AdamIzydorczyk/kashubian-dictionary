package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

data class TranslationCriteriaExpression(
    val id: NumericCriteria?,
    val polish: StringCriteria?,
    val normalizedPolish: NormalizedCriteria?,
    val english: StringCriteria?,
    val normalizedEnglish: NormalizedCriteria?,
    val german: StringCriteria?,
    val normalizedGerman: NormalizedCriteria?,
    val ukrainian: StringCriteria?,
    val normalizedUkrainian: NormalizedCriteria?)