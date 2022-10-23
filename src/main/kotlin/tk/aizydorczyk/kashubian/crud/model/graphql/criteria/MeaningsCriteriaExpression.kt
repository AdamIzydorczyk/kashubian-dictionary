package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

import tk.aizydorczyk.kashubian.crud.query.graphql.base.CriteriaExpression

data class MeaningsCriteriaExpression(
    val id: NumericCriteria?,
    val origin: StringCriteria?,
    val definition: StringCriteria?,
    val hyperonyms: JsonCriteria?,
    val hyponyms: JsonCriteria?,
    val synonyms: SynonymsCriteriaExpression?,
    val proverbs: ProverbsCriteriaExpression?,
    val translation: TranslationCriteriaExpression?,
    val quotes: QuotesCriteriaExpression?,
    val antonyms: AntonymsCriteriaExpression?,
    val examples: ExamplesCriteriaExpression?,
    val idioms: IdiomsCriteriaExpression?) : CriteriaExpression