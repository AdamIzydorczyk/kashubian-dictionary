package tk.aizydorczyk.kashubian.crud.query.graphql.entry

import org.jooq.QueryPart
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
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryBaseId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryBaseTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryBaseWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryBases
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryBasesWithAlias
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryDerivatives
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryDerivativesWithAlias
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryNormalizedWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryPartOfSpeech
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryPartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryPriority
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryVariation
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.entryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleExample
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.exampleTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningDefinition
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
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.meaningsCount
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherEntryId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherEntryTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherEntryWord
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.otherTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.phrasalVerbId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.phrasalVerbNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.phrasalVerbPhrasalVerb
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.phrasalVerbTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbProverb
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.proverbTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteNote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteQuote
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.quoteTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.soundFileFileName
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.soundFileId
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.soundFileTable
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLColumnsAndTables.Companion.soundFileType
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
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.BASES_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.BASE_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.DEFINITION_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.DERIVATIVES_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ENGLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLES_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.EXAMPLE_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.FILE_NAME
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.GERMAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPERONYMS_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPERONYM_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.HYPONYMS_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ID_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRY_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.KASHUBIAN_ENTRY_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANINGS_COUNT_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANINGS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANING_SIMPLIFIED_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.MEANING_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_ENGLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_GERMAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_POLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_UKRAINIAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NORMALIZED_WORD_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.NOTE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.ORIGIN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.OTHERS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.OTHER_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.OTHER_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PART_OF_SPEECH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PART_OF_SPEECH_SUB_TYPE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PHRASAL_VERBS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PHRASAL_VERB_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PHRASAL_VERB_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.POLISH_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PRIORITY_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERBS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERB_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.PROVERB_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTES_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.QUOTE_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SELECT_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SOUND_FILE_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SOUND_FILE_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYMS_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYM_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.SYNONYM_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.TRANSLATION_NODE
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.TRANSLATION_TYPE_PREFIX
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.TYPE_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.UKRAINIAN_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.VARIATION_FIELD
import tk.aizydorczyk.kashubian.crud.model.value.GraphQLFields.Companion.WORD_FIELD
import tk.aizydorczyk.kashubian.crud.query.graphql.base.JoinTableWithCondition


object KashubianEntryQueryRelations {
    internal val FIND_ALL_FIELD_TO_JOIN_RELATIONS = mapOf(
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE" to
                    Triple(otherTable(),
                            entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                            otherId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$SOUND_FILE_NODE" to
                    Triple(soundFileTable(),
                            entryTable().ID.eq(soundFileTable().KASHUBIAN_ENTRY_ID),
                            soundFileId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE$OTHER_TYPE_PREFIX$OTHER_NODE" to
                    Triple(otherEntryTable(),
                            otherTable().OTHER_ID.eq(otherEntryTable().ID),
                            otherEntryId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$BASE_NODE" to
                    Triple(entryBaseTable(),
                            entryTable().BASE_ID.eq(entryBaseTable().ID),
                            entryBaseId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE" to
                    Triple(meaningTable(),
                            entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                            meaningId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE" to
                    Triple(translationTable(),
                            meaningTable().ID.eq(translationTable().MEANING_ID),
                            translationId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE" to
                    Triple(meaningHyperonymTable(),
                            meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                            meaningHyperonymId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(meaningHyperonymEntryTable(),
                            meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(meaningHyperonymEntryTable().ID),
                            meaningHyperonymEntryId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PROVERBS_NODE" to
                    Triple(proverbTable(),
                            meaningTable().ID.eq(proverbTable().MEANING_ID),
                            proverbId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$QUOTES_NODE" to
                    Triple(quoteTable(),
                            meaningTable().ID.eq(quoteTable().MEANING_ID),
                            quoteId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$EXAMPLES_NODE" to
                    Triple(exampleTable(),
                            meaningTable().ID.eq(exampleTable().MEANING_ID),
                            exampleId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PHRASAL_VERBS_NODE" to
                    Triple(phrasalVerbTable(),
                            meaningTable().ID.eq(phrasalVerbTable().MEANING_ID),
                            phrasalVerbId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE" to
                    Triple(synonymTable(),
                            meaningTable().ID.eq(synonymTable().MEANING_ID),
                            synonymId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE" to
                    Triple(antonymTable(),
                            meaningTable().ID.eq(antonymTable().MEANING_ID),
                            antonymId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE" to
                    Triple(synonymMeaningTable(),
                            synonymTable().SYNONYM_ID.eq(synonymMeaningTable().ID),
                            synonymMeaningId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE" to
                    Triple(antonymMeaningTable(),
                            antonymTable().ANTONYM_ID.eq(antonymMeaningTable().ID),
                            antonymMeaningId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(synonymMeaningEntryTable(),
                            synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(synonymMeaningEntryTable().ID),
                            synonymMeaningEntryId()),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE" to
                    Triple(antonymMeaningEntryTable(),
                            antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(antonymMeaningEntryTable().ID),
                            antonymMeaningEntryId())
    )
    internal val FIND_ONE_FIELD_TO_JOIN_RELATIONS = FIND_ALL_FIELD_TO_JOIN_RELATIONS.mapKeys {
        it.key.removePrefix("$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX/")
    }
    internal val FIND_ALL_FIELD_TO_COLUMN_RELATIONS = mapOf(
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$ID_FIELD" to
                    entryId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$WORD_FIELD" to
                    entryWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$NORMALIZED_WORD_FIELD" to
                    entryNormalizedWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$VARIATION_FIELD" to
                    entryVariation(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$PRIORITY_FIELD" to
                    entryPriority(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$NOTE_FIELD" to
                    entryNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$PART_OF_SPEECH_FIELD" to
                    entryPartOfSpeech(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$PART_OF_SPEECH_SUB_TYPE_FIELD" to
                    entryPartOfSpeechSubType(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_COUNT_FIELD" to
                    meaningsCount(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$BASE_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    entryBaseId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$BASE_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    entryBaseWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$BASES_FIELD" to
                    entryBasesWithAlias(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$DERIVATIVES_FIELD" to
                    entryDerivativesWithAlias(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE$OTHER_TYPE_PREFIX$ID_FIELD" to
                    otherId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE$OTHER_TYPE_PREFIX$NOTE_FIELD" to
                    otherNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE$OTHER_TYPE_PREFIX$OTHER_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    otherEntryId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$OTHERS_NODE$OTHER_TYPE_PREFIX$OTHER_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    otherEntryWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$SOUND_FILE_NODE$SOUND_FILE_TYPE_PREFIX$ID_FIELD" to
                    soundFileId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$SOUND_FILE_NODE$SOUND_FILE_TYPE_PREFIX$TYPE_FIELD" to
                    soundFileType(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$SOUND_FILE_NODE$SOUND_FILE_TYPE_PREFIX$FILE_NAME" to
                    soundFileFileName(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ID_FIELD" to
                    meaningId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ORIGIN_FIELD" to
                    meaningOrigin(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$DEFINITION_FIELD" to
                    meaningDefinition(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    meaningHyperonymId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    meaningHyperonymDefinition(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    meaningHyperonymEntryId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    meaningHyperonymEntryWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPERONYMS_FIELD" to
                    meaningHyperonymsWithAlias(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$HYPONYMS_FIELD" to
                    meaningHyponymsWithAlias(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$ID_FIELD" to
                    translationId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$POLISH_FIELD" to
                    translationPolish(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_POLISH_FIELD" to
                    translationPolishNormalizedWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$ENGLISH_FIELD" to
                    translationEnglish(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_ENGLISH_FIELD" to
                    translationNormalizedEnglish(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$GERMAN_FIELD" to
                    translationGerman(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_GERMAN_FIELD" to
                    translationNormalizedGerman(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$UKRAINIAN_FIELD" to
                    translationUkrainian(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$TRANSLATION_NODE$TRANSLATION_TYPE_PREFIX$NORMALIZED_UKRAINIAN_FIELD" to
                    translationNormalizedUkrainian(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$ID_FIELD" to
                    proverbId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$NOTE_FIELD" to
                    proverbNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PROVERBS_NODE$PROVERB_TYPE_PREFIX$PROVERB_FIELD" to
                    proverbProverb(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$ID_FIELD" to
                    quoteId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$NOTE_FIELD" to
                    quoteNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$QUOTES_NODE$QUOTE_TYPE_PREFIX$QUOTE_FIELD" to
                    quoteQuote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$ID_FIELD" to
                    exampleId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$NOTE_FIELD" to
                    exampleNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$EXAMPLES_NODE$EXAMPLE_TYPE_PREFIX$EXAMPLE_FIELD" to
                    exampleExample(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PHRASAL_VERBS_NODE$PHRASAL_VERB_TYPE_PREFIX$ID_FIELD" to
                    phrasalVerbId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PHRASAL_VERBS_NODE$PHRASAL_VERB_TYPE_PREFIX$NOTE_FIELD" to
                    phrasalVerbNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$PHRASAL_VERBS_NODE$PHRASAL_VERB_TYPE_PREFIX$PHRASAL_VERB_FIELD" to
                    phrasalVerbPhrasalVerb(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$ID_FIELD" to
                    synonymId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$NOTE_FIELD" to
                    synonymNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ID_FIELD" to
                    antonymId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$NOTE_FIELD" to
                    antonymNote(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    synonymMeaningId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    synonymMeaningDefinition(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    antonymMeaningId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$DEFINITION_FIELD" to
                    antonymMeaningDefinition(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    synonymMeaningEntryId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$SYNONYMS_NODE$SYNONYM_TYPE_PREFIX$SYNONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    synonymMeaningEntryWord(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$ID_FIELD" to
                    antonymMeaningEntryId(),
            "$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX$KASHUBIAN_ENTRY_TYPE_PREFIX$MEANINGS_NODE$MEANING_TYPE_PREFIX$ANTONYMS_NODE$ANTONYM_TYPE_PREFIX$ANTONYM_NODE$MEANING_SIMPLIFIED_TYPE_PREFIX$KASHUBIAN_ENTRY_NODE$KASHUBIAN_ENTRY_SIMPLIFIED_TYPE_PREFIX$WORD_FIELD" to
                    antonymMeaningEntryWord()
    )

    internal val FIND_ONE_FIELD_TO_COLUMN_RELATIONS = FIND_ALL_FIELD_TO_COLUMN_RELATIONS.mapKeys {
        it.key.removePrefix("$KASHUBIAN_ENTRIES_PAGED_TYPE_PREFIX$SELECT_PREFIX/")
    }

    val CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN: Map<String, Pair<QueryPart, List<JoinTableWithCondition>>> =
        listOf(
                "$SELECT_PREFIX$ID_FIELD" to (entryTable().ID joinedBy emptyList()),
                "$SELECT_PREFIX$NOTE_FIELD" to (entryTable().NOTE joinedBy emptyList()),
                "$SELECT_PREFIX$WORD_FIELD" to (entryTable().WORD joinedBy emptyList()),
                "$SELECT_PREFIX$VARIATION_FIELD" to (entryTable().VARIATION joinedBy emptyList()),
                "$SELECT_PREFIX$BASES_FIELD" to (entryBases() joinedBy emptyList()),
                "$SELECT_PREFIX$DERIVATIVES_FIELD" to (entryDerivatives() joinedBy emptyList()),
                "$SELECT_PREFIX$NORMALIZED_WORD_FIELD" to (entryTable().NORMALIZED_WORD joinedBy emptyList()),
                "$SELECT_PREFIX$PRIORITY_FIELD" to (entryTable().PRIORITY joinedBy emptyList()),
                "$SELECT_PREFIX$SOUND_FILE_NODE$ID_FIELD" to (soundFileTable().ID joinedBy emptyList()),
                "$SELECT_PREFIX$SOUND_FILE_NODE$TYPE_FIELD" to (soundFileTable().TYPE joinedBy emptyList()),
                "$SELECT_PREFIX$SOUND_FILE_NODE$FILE_NAME" to (soundFileTable().FILE_NAME joinedBy emptyList()),
                "$SELECT_PREFIX$BASE_NODE$ID_FIELD" to (entryBaseTable().ID joinedBy listOf(entryBaseTable() on entryTable().BASE_ID.eq(
                        entryBaseTable().ID))),
                "$SELECT_PREFIX$BASE_NODE$WORD_FIELD" to (entryBaseTable().WORD joinedBy listOf(entryBaseTable() on entryTable().BASE_ID.eq(
                        entryBaseTable().ID))),
                "$SELECT_PREFIX$OTHERS_NODE$ID_FIELD" to (
                        otherTable().ID joinedBy
                                listOf(otherTable() on entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$OTHERS_NODE$NOTE_FIELD" to (
                        otherTable().NOTE joinedBy
                                listOf(otherTable() on entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$OTHERS_NODE$OTHER_NODE$ID_FIELD" to (otherEntryTable().ID joinedBy
                        listOf(otherTable() on entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                                otherEntryTable() on otherTable().OTHER_ID.eq(otherEntryTable().ID))),
                "$SELECT_PREFIX$OTHERS_NODE$OTHER_NODE$WORD_FIELD" to (otherEntryTable().WORD joinedBy
                        listOf(otherTable() on entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                                otherEntryTable() on otherTable().OTHER_ID.eq(otherEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ID_FIELD" to (meaningTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ORIGIN_FIELD" to (meaningTable().ORIGIN joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$DEFINITION_FIELD" to (meaningTable().DEFINITION joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPERONYM_NODE$ID_FIELD" to (meaningHyperonymTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPERONYM_NODE$DEFINITION_FIELD" to (meaningHyperonymTable().DEFINITION joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPERONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (meaningHyperonymEntryTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                                meaningHyperonymEntryTable() on meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(
                                        meaningHyperonymEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPERONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (meaningHyperonymEntryTable().WORD joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                meaningHyperonymTable() on meaningTable().HYPERONYM_ID.eq(meaningHyperonymTable().ID),
                                meaningHyperonymEntryTable() on meaningHyperonymTable().KASHUBIAN_ENTRY_ID.eq(
                                        meaningHyperonymEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPERONYMS_FIELD" to (meaningHyperonyms() joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$HYPONYMS_FIELD" to (meaningHyponyms() joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$ID_FIELD" to (synonymTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$NOTE_FIELD" to (synonymTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$SYNONYM_NODE$ID_FIELD" to (synonymMeaningTable() joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$SYNONYM_NODE$DEFINITION_FIELD" to (synonymMeaningTable().DEFINITION joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$SYNONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (synonymMeaningEntryTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$SYNONYMS_NODE$SYNONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (synonymMeaningEntryTable().WORD joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PROVERBS_NODE$ID_FIELD" to (proverbTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PROVERBS_NODE$NOTE_FIELD" to (proverbTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PROVERBS_NODE$PROVERB_FIELD" to (proverbTable().PROVERB_ joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$ID_FIELD" to (translationTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$POLISH_FIELD" to (translationTable().POLISH joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$NORMALIZED_POLISH_FIELD" to (translationTable().NORMALIZED_POLISH joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$ENGLISH_FIELD" to (translationTable().ENGLISH joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$NORMALIZED_ENGLISH_FIELD" to (translationTable().NORMALIZED_ENGLISH joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$GERMAN_FIELD" to (translationTable().GERMAN joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$NORMALIZED_GERMAN_FIELD" to (translationTable().NORMALIZED_GERMAN joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$UKRAINIAN_FIELD" to (translationTable().UKRAINIAN joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$TRANSLATION_NODE$NORMALIZED_UKRAINIAN_FIELD" to (translationTable().NORMALIZED_UKRAINIAN joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$QUOTES_NODE$ID_FIELD" to (quoteTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$QUOTES_NODE$NOTE_FIELD" to (quoteTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$QUOTES_NODE$QUOTE_FIELD" to (quoteTable().QUOTE_ joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$ID_FIELD" to (antonymTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$NOTE_FIELD" to (antonymTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$ANTONYM_NODE$ID_FIELD" to (antonymMeaningTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$ANTONYM_NODE$DEFINITION_FIELD" to (antonymMeaningTable().DEFINITION joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$ANTONYM_NODE$KASHUBIAN_ENTRY_NODE$ID_FIELD" to (antonymMeaningEntryTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$ANTONYMS_NODE$ANTONYM_NODE$KASHUBIAN_ENTRY_NODE$WORD_FIELD" to (antonymMeaningEntryTable().WORD joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$EXAMPLES_NODE$ID_FIELD" to (exampleTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$EXAMPLES_NODE$NOTE_FIELD" to (exampleTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$EXAMPLES_NODE$EXAMPLE_FIELD" to (exampleTable().EXAMPLE_ joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PHRASAL_VERBS_NODE$ID_FIELD" to (phrasalVerbTable().ID joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PHRASAL_VERBS_NODE$NOTE_FIELD" to (phrasalVerbTable().NOTE joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "$SELECT_PREFIX$MEANINGS_NODE$PHRASAL_VERBS_NODE$PHRASAL_VERB_FIELD" to (phrasalVerbTable().PHRASAL_VERB_ joinedBy
                        listOf(meaningTable() on entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID)))
        ).map { criteriaAndField ->
            listOf(".EQ", "._LIKE", ".LIKE", ".BY_NORMALIZED", ".BY_JSON").map {
                criteriaAndField.fieldPath() + it to
                        (criteriaAndField.fieldWithJoins().field to criteriaAndField.fieldWithJoins().joins)
            }
        }.flatten().associate { it.first to it.second }

}
