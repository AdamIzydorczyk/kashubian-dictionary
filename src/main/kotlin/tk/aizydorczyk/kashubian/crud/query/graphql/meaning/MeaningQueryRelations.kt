package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import tk.aizydorczyk.kashubian.crud.extension.fieldPath
import tk.aizydorczyk.kashubian.crud.extension.fieldWithJoins
import tk.aizydorczyk.kashubian.crud.extension.joinedBy
import tk.aizydorczyk.kashubian.crud.extension.on
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningDefinition
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningEntryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningEntryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningEntryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymMeaningTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.antonymTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleExample
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.idiomId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.idiomNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.idiomPhrasalVerb
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.idiomTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningDefinition
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningEntryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningEntryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningEntryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymDefinition
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymEntryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymEntryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymEntryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonyms
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyperonymsWithAlias
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyponyms
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningHyponymsWithAlias
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningOrigin
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbProverb
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteQuote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningDefinition
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningEntryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningEntryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningEntryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymMeaningTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.synonymTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationEnglish
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationGerman
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationNormalizedEnglish
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationNormalizedGerman
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationNormalizedUkrainian
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationPolish
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationPolishNormalizedWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.translationUkrainian
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ANTONYMS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ANTONYM_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ANTONYM_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.DEFINITION_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ENGLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLES_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLE_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.GERMAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPERONYMS_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPERONYM_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPONYMS_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.IDIOMS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.IDIOM_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.IDIOM_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ID_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRY_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANINGS_PAGED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANING_SIMPLIFIED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANING_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_ENGLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_GERMAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_POLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_UKRAINIAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_WORD_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NOTE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ORIGIN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.POLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERBS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERB_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERB_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTES_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTE_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SELECT_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYMS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYM_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYM_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.TRANSLATION_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.TRANSLATION_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.UKRAINIAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.WORD_FIELD


object MeaningQueryRelations {
    internal val FIND_ALL_FIELD_TO_JOIN_RELATIONS = mapOf(
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE" to
                    Triple(translationTable(),
                            meaningTable().ID.eq(translationTable().MEANING_ID),
                            translationId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE" to
                    Triple(meaningHyperonymTable(),
                            meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                            meaningHyperonymId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(meaningHyperonymEntryTable(),
                            meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(meaningHyperonymEntryTable().ID),
                            meaningHyperonymEntryId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$PROVERBS_NODE" to
                    Triple(proverbTable(),
                            meaningTable().ID.eq(proverbTable().MEANING_ID),
                            proverbId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$QUOTES_NODE" to
                    Triple(quoteTable(),
                            meaningTable().ID.eq(quoteTable().MEANING_ID),
                            quoteId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$EXAMPLES_NODE" to
                    Triple(exampleTable(),
                            meaningTable().ID.eq(exampleTable().MEANING_ID),
                            exampleId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$IDIOMS_NODE" to
                    Triple(idiomTable(),
                            meaningTable().ID.eq(idiomTable().MEANING_ID),
                            idiomId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE" to
                    Triple(synonymTable(),
                            meaningTable().ID.eq(synonymTable().MEANING_ID),
                            synonymId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE" to
                    Triple(antonymTable(),
                            meaningTable().ID.eq(antonymTable().MEANING_ID),
                            antonymId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE" to
                    Triple(synonymMeaningTable(),
                            synonymTable().SYNONYM_ID.eq(synonymMeaningTable().ID),
                            synonymMeaningId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE" to
                    Triple(antonymMeaningTable(),
                            antonymTable().ANTONYM_ID.eq(antonymMeaningTable().ID),
                            antonymMeaningId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(synonymMeaningEntryTable(),
                            synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(synonymMeaningEntryTable().ID),
                            synonymMeaningEntryId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(antonymMeaningEntryTable(),
                            antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(antonymMeaningEntryTable().ID),
                            antonymMeaningEntryId()),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(meaningEntryTable(),
                            meaningTable().KASHUBIAN_ENTRY_ID.eq(meaningEntryTable().ID),
                            meaningEntryId())
    )

    internal val FIND_ONE_FIELD_TO_JOIN_RELATIONS = FIND_ALL_FIELD_TO_JOIN_RELATIONS.mapKeys {
        it.key.removePrefix("$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX/")
    }

    internal val FIND_ALL_FIELD_TO_COLUMN_RELATIONS = mapOf(
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ID_FIELD" to
                    meaningId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ORIGIN_FIELD" to
                    meaningOrigin(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$DEFINITION_FIELD" to
                    meaningDefinition(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    meaningHyperonymId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    meaningHyperonymDefinition(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    meaningHyperonymEntryId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    meaningHyperonymEntryWord(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPERONYMS_FIELD" to
                    meaningHyperonymsWithAlias(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$HYPONYMS_FIELD" to
                    meaningHyponymsWithAlias(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$ID_FIELD" to
                    translationId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$POLISH_FIELD" to
                    translationPolish(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_POLISH_FIELD" to
                    translationPolishNormalizedWord(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$ENGLISH_FIELD" to
                    translationEnglish(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_ENGLISH_FIELD" to
                    translationNormalizedEnglish(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$GERMAN_FIELD" to
                    translationGerman(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_GERMAN_FIELD" to
                    translationNormalizedGerman(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$UKRAINIAN_FIELD" to
                    translationUkrainian(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_UKRAINIAN_FIELD" to
                    translationNormalizedUkrainian(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$ID_FIELD" to
                    proverbId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$NOTE_FIELD" to
                    proverbNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$PROVERB_FIELD" to
                    proverbProverb(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$ID_FIELD" to
                    quoteId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$NOTE_FIELD" to
                    quoteNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$QUOTE_FIELD" to
                    quoteQuote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$ID_FIELD" to
                    exampleId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$NOTE_FIELD" to
                    exampleNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$EXAMPLE_FIELD" to
                    exampleExample(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$IDIOMS_NODE$IDIOM_TYPE_PREFIX$ID_FIELD" to
                    idiomId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$IDIOMS_NODE$IDIOM_TYPE_PREFIX$NOTE_FIELD" to
                    idiomNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$IDIOMS_NODE$IDIOM_TYPE_PREFIX$IDIOM_FIELD" to
                    idiomPhrasalVerb(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$ID_FIELD" to
                    synonymId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$NOTE_FIELD" to
                    synonymNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ID_FIELD" to
                    antonymId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$NOTE_FIELD" to
                    antonymNote(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    synonymMeaningId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    synonymMeaningDefinition(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    antonymMeaningId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    antonymMeaningDefinition(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    synonymMeaningEntryId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    synonymMeaningEntryWord(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    antonymMeaningEntryId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    antonymMeaningEntryWord(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    meaningEntryId(),
            "$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX$MEANING_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    meaningEntryWord()
    )

    internal val FIND_ONE_FIELD_TO_COLUMN_RELATIONS = FIND_ALL_FIELD_TO_COLUMN_RELATIONS.mapKeys {
        it.key.removePrefix("$MEANINGS_PAGED_TYPE_PREFIX$SELECT_PREFIX/")
    }

    val CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN =
        listOf(
                "$SELECT_PREFIX$ID_FIELD" to (meaningTable().ID joinedBy
                        emptyList()),
                "$SELECT_PREFIX$ORIGIN_FIELD" to (meaningTable().ORIGIN joinedBy emptyList()),
                "$SELECT_PREFIX$DEFINITION_FIELD" to (meaningTable().DEFINITION joinedBy emptyList()),
                "$SELECT_PREFIX$HYPERONYM_NODE$ID_FIELD" to (meaningHyperonymTable().ID joinedBy
                        listOf(meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID))),
                "$SELECT_PREFIX$HYPERONYM_NODE$DEFINITION_FIELD" to (meaningHyperonymTable().DEFINITION joinedBy
                        listOf(meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID))),
                "$SELECT_PREFIX$HYPERONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (meaningHyperonymEntryTable().ID joinedBy
                        listOf(meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                                meaningHyperonymEntryTable() on meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(
                                        meaningHyperonymEntryTable().ID))),
                "$SELECT_PREFIX$HYPERONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (meaningHyperonymEntryTable().WORD joinedBy
                        listOf(meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                                meaningHyperonymEntryTable() on meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(
                                        meaningHyperonymEntryTable().ID))),
                "$SELECT_PREFIX$HYPERONYM_NODE$KASHUBIAN_ENTRY_NODE$NORMALIZED_WORD_FIELD" to (meaningHyperonymEntryTable().NORMALIZED_WORD joinedBy
                        listOf(meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                                meaningHyperonymEntryTable() on meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(
                                        meaningHyperonymEntryTable().ID))),
                "$SELECT_PREFIX$HYPERONYMS_FIELD" to (meaningHyperonyms() joinedBy emptyList()),
                "$SELECT_PREFIX$HYPONYMS_FIELD" to (meaningHyponyms() joinedBy emptyList()),
                "$SELECT_PREFIX$SYNONYMS_NODE$ID_FIELD" to (synonymTable().ID joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$NOTE_FIELD" to (synonymTable().NOTE joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$SYNONYM_NODE$ID_FIELD" to (synonymMeaningTable() joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$SYNONYM_NODE$DEFINITION_FIELD" to (synonymMeaningTable().DEFINITION joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$SYNONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (synonymMeaningEntryTable().ID joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$SYNONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (synonymMeaningEntryTable().WORD joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$SYNONYMS_NODE$SYNONYM_NODE$KASHUBIAN_ENTRY_NODE$NORMALIZED_WORD_FIELD" to (synonymMeaningEntryTable().NORMALIZED_WORD joinedBy
                        listOf(synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$PROVERBS_NODE$ID_FIELD" to (proverbTable().ID joinedBy
                        listOf(proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$PROVERBS_NODE$NOTE_FIELD" to (proverbTable().NOTE joinedBy
                        listOf(proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$PROVERBS_NODE$PROVERB_FIELD" to (proverbTable().PROVERB_ joinedBy
                        listOf(proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$ID_FIELD" to (translationTable().ID joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$POLISH_FIELD" to (translationTable().POLISH joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$NORMALIZED_POLISH_FIELD" to (translationTable().NORMALIZED_POLISH joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$ENGLISH_FIELD" to (translationTable().ENGLISH joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$NORMALIZED_ENGLISH_FIELD" to (translationTable().NORMALIZED_ENGLISH joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$GERMAN_FIELD" to (translationTable().GERMAN joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$NORMALIZED_GERMAN_FIELD" to (translationTable().NORMALIZED_GERMAN joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$UKRAINIAN_FIELD" to (translationTable().UKRAINIAN joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$TRANSLATION_NODE$NORMALIZED_UKRAINIAN_FIELD" to (translationTable().NORMALIZED_UKRAINIAN joinedBy
                        listOf(translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$QUOTES_NODE$ID_FIELD" to (quoteTable().ID joinedBy
                        listOf(quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$QUOTES_NODE$NOTE_FIELD" to (quoteTable().NOTE joinedBy
                        listOf(quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$QUOTES_NODE$QUOTE_FIELD" to (quoteTable().QUOTE_ joinedBy
                        listOf(quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ID_FIELD" to (antonymTable().ID joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$NOTE_FIELD" to (antonymTable().NOTE joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ANTONYM_NODE$ID_FIELD" to (antonymMeaningTable().ID joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ANTONYM_NODE$DEFINITION_FIELD" to (antonymMeaningTable().DEFINITION joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ANTONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (antonymMeaningEntryTable().ID joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ANTONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (antonymMeaningEntryTable().WORD joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$ANTONYMS_NODE$ANTONYM_NODE$KASHUBIAN_ENTRY_NODE$NORMALIZED_WORD_FIELD" to (antonymMeaningEntryTable().NORMALIZED_WORD joinedBy
                        listOf(antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$EXAMPLES_NODE$ID_FIELD" to (exampleTable().ID joinedBy
                        listOf(exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$EXAMPLES_NODE$NOTE_FIELD" to (exampleTable().NOTE joinedBy
                        listOf(exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$EXAMPLES_NODE$EXAMPLE_FIELD" to (exampleTable().EXAMPLE_ joinedBy
                        listOf(exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$IDIOMS_NODE$ID_FIELD" to (idiomTable().ID joinedBy
                        listOf(idiomTable() on meaningTable().ID.eq(idiomTable().MEANING_ID))),
                "$SELECT_PREFIX$IDIOMS_NODE$NOTE_FIELD" to (idiomTable().NOTE joinedBy
                        listOf(idiomTable() on meaningTable().ID.eq(idiomTable().MEANING_ID))),
                "$SELECT_PREFIX$IDIOMS_NODE$IDIOM_FIELD" to (idiomTable().IDIOM_ joinedBy
                        listOf(idiomTable() on meaningTable().ID.eq(idiomTable().MEANING_ID))),
                "$SELECT_PREFIX$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (meaningEntryTable().ID joinedBy
                        listOf(meaningEntryTable() on meaningTable().KASHUBIAN_ENTRY_ID.eq(meaningEntryTable().ID))),
                "$SELECT_PREFIX$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (meaningEntryTable().WORD joinedBy
                        listOf(meaningEntryTable() on meaningTable().KASHUBIAN_ENTRY_ID.eq(meaningEntryTable().ID))),
                "$SELECT_PREFIX$KASHUBIAN_ENTRY_NODE$NORMALIZED_WORD_FIELD" to (meaningEntryTable().NORMALIZED_WORD joinedBy
                        listOf(meaningEntryTable() on meaningTable().KASHUBIAN_ENTRY_ID.eq(meaningEntryTable().ID)))
        ).map { criteriaAndField ->
            listOf(".EQ", ".LIKE_", ".LIKE", ".BY_NORMALIZED", ".BY_JSON").map {
                criteriaAndField.fieldPath() + it to
                        (criteriaAndField.fieldWithJoins().field to criteriaAndField.fieldWithJoins().joins)
            }
        }.flatten().associate { it.first to it.second }

}

