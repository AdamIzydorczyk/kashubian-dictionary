package tk.aizydorczyk.kashubian.crud.model.graphql

data class KashubianEntryCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val word: StringCriteria?,
    val normalizedWord: StringCriteria?,
    val priority: BooleanCriteria?,
    val soundFile: SoundFileCriteriaExpression?,
    val others: OthersCriteriaExpression?,
    val meanings: MeaningsCriteriaExpression?)

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
    val phrasalVerbs: PhrasalVerbsCriteriaExpression?)

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
    val normalizedPolish: StringCriteria?,
    val english: StringCriteria?,
    val normalizedEnglish: StringCriteria?,
    val german: StringCriteria?,
    val normalizedGerman: StringCriteria?,
    val ukrainian: StringCriteria?,
    val normalizedUkrainian: StringCriteria?)

data class NumericCriteria(val EQ: String?)
data class BooleanCriteria(val EQ: Boolean?)
data class StringCriteria(val EQ: String?, val LIKE: String?, val _LIKE: String?)
data class PageCriteria(val start: Int, val limit: Int)