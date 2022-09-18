package tk.aizydorczyk.kashubian.crud.model.graphql

import tk.aizydorczyk.kashubian.crud.query.graphql.base.CriteriaExpression

data class KashubianEntryCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val word: StringCriteria?,
    val variation: JsonCriteria?,
    val normalizedWord: NormalizedCriteria?,
    val priority: BooleanCriteria?,
    val soundFile: SoundFileCriteriaExpression?,
    val others: OthersCriteriaExpression?,
    val meanings: MeaningsCriteriaExpression?) : CriteriaExpression

data class SoundFileCriteriaExpression(
    val id: NumericCriteria?,
    val type: StringCriteria?,
    val fileName: StringCriteria?)

data class OthersCriteriaExpression(
    val id: NumericCriteria,
    val note: StringCriteria)

data class KashubianEntryCriteriaExpressionSimplified(
    val id: NumericCriteria?,
    val word: StringCriteria?)

data class MeaningsCriteriaExpression(
    val id: NumericCriteria?,
    val origin: StringCriteria?,
    val definition: StringCriteria?,
    val synonyms: SynonymsCriteriaExpression?,
    val proverbs: ProverbsCriteriaExpression?,
    val translation: TranslationCriteriaExpression?,
    val quotes: QuotesCriteriaExpression?,
    val antonyms: AntonymsCriteriaExpression?,
    val examples: ExamplesCriteriaExpression?,
    val phrasalVerbs: PhrasalVerbsCriteriaExpression?) : CriteriaExpression

data class MeaningsCriteriaExpressionSimplified(
    val id: NumericCriteria?,
    val definition: StringCriteria?,
    val kashubianEntry: KashubianEntryCriteriaExpressionSimplified?)

data class SynonymsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val synonym: MeaningsCriteriaExpressionSimplified?)

data class ProverbsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val proverb: StringCriteria?)

data class QuotesCriteriaExpression(
    val id: NumericCriteria?,
    val note: NumericCriteria?,
    val quote: StringCriteria?)

data class AntonymsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val antonym: MeaningsCriteriaExpressionSimplified?)

data class ExamplesCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val example: StringCriteria?)

data class PhrasalVerbsCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val phrasalVerb: StringCriteria?)

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


data class NumericCriteria(val EQ: Int?)
data class BooleanCriteria(val EQ: Boolean?)
data class StringCriteria(val EQ: String?, val LIKE: String?, val _LIKE: String?)
data class NormalizedCriteria(val BY_NORMALIZED: String?)
data class PageCriteria(val start: Int, val limit: Int)
data class JsonCriteria(val BY_JSON: String?)