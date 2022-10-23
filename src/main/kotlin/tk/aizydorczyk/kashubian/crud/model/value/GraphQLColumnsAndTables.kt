package tk.aizydorczyk.kashubian.crud.model.value

import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.select
import org.jooq.impl.DSL.selectCount
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Routines
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables

class GraphQLColumnsAndTables {
    companion object {
        fun antonymMeaningEntryId() = antonymMeaningEntryTable().ID.`as`("antonym_meaning_entry_id")
        fun synonymMeaningEntryId() = synonymMeaningEntryTable().ID.`as`("synonym_meaning_entry_id")
        fun antonymMeaningId() = antonymMeaningTable().ID.`as`("antonym_meaning_id")
        fun synonymMeaningId() = synonymMeaningTable().ID.`as`("synonym_meaning_id")
        fun antonymId() = antonymTable().ID.`as`("antonym_id")
        fun synonymId() = synonymTable().ID.`as`("synonym_id")
        fun idiomId() = idiomTable().ID.`as`("idiom_id")
        fun exampleId() = exampleTable().ID.`as`("example_id")
        fun quoteId() = quoteTable().ID.`as`("quote_id")
        fun proverbId() = proverbTable().ID.`as`("proverb_id")
        fun translationId() = translationTable().ID.`as`("translation_id")
        fun meaningId() = meaningTable().ID.`as`("meaning_id")
        fun otherEntryId() = otherEntryTable().ID.`as`("other_entry_id")
        fun soundFileId() = soundFileTable().ID.`as`("sound_file_id")
        fun otherId() = otherTable().ID.`as`("other_id")
        fun entryId() = Tables.KASHUBIAN_ENTRY.`as`("entry").ID.`as`("entry_id")
        fun antonymMeaningEntryWord() = antonymMeaningEntryTable().WORD.`as`("antonym_meaning_entry_word")
        fun synonymMeaningEntryWord() = synonymMeaningEntryTable().WORD.`as`("synonym_meaning_entry_word")
        fun antonymMeaningDefinition() = antonymMeaningTable().DEFINITION.`as`("antonym_meaning_definition")
        fun synonymMeaningDefinition() = synonymMeaningTable().DEFINITION.`as`("synonym_meaning_definition")
        fun antonymNote() = antonymTable().NOTE.`as`("antonym_note")
        fun synonymNote() = synonymTable().NOTE.`as`("synonym_note")
        fun idiomPhrasalVerb() = idiomTable().IDIOM_.`as`("idiom_idiom")
        fun idiomNote() = idiomTable().NOTE.`as`("idiom_note")
        fun exampleExample() = exampleTable().EXAMPLE_.`as`("example_example")
        fun exampleNote() = exampleTable().NOTE.`as`("example_note")
        fun quoteQuote() = quoteTable().QUOTE_.`as`("quote_quote")
        fun quoteNote() = quoteTable().NOTE.`as`("quote_note")
        fun proverbProverb() = proverbTable().PROVERB_.`as`("proverb_proverb")
        fun proverbNote() = proverbTable().NOTE.`as`("proverb_note")
        fun translationNormalizedUkrainian() =
            translationTable().NORMALIZED_UKRAINIAN.`as`("translation_normalized_ukrainian")

        fun translationUkrainian() = translationTable().UKRAINIAN.`as`("translation_ukrainian")
        fun translationNormalizedGerman() =
            translationTable().NORMALIZED_GERMAN.`as`("translation_normalized_german")
        fun translationGerman() = translationTable().GERMAN.`as`("translation_german")
        fun translationNormalizedEnglish() =
            translationTable().NORMALIZED_ENGLISH.`as`("translation_normalized_english")
        fun translationEnglish() = translationTable().ENGLISH.`as`("translation_english")
        fun translationPolishNormalizedWord() =
            translationTable().NORMALIZED_POLISH.`as`("translation_normalized_polish")
        fun meaningDefinition() = meaningTable().DEFINITION.`as`("meaning_definition")
        fun meaningOrigin() = meaningTable().ORIGIN.`as`("meaning_origin")
        fun translationPolish() = translationTable().POLISH.`as`("translation_polish")
        fun meaningHyponymsWithAlias() =
            field(select(Routines.findHyponyms(meaningTable().ID))).`as`("meaning_hyponyms")
        fun meaningHyperonymsWithAlias() =
            field(select(Routines.findHyperonyms(meaningTable().ID))).`as`("meaning_hyperonyms")
        fun meaningHyponyms() =
            field(select(Routines.findHyponyms(meaningTable().ID)))

        fun meaningHyperonyms() =
            field(select(Routines.findHyperonyms(meaningTable().ID)))

        fun soundFileFileName() = soundFileTable().FILE_NAME.`as`("sound_file_file_name")
        fun soundFileType() = soundFileTable().TYPE.`as`("sound_file_type")
        fun otherEntryWord() = otherEntryTable().WORD.`as`("other_entry_word")
        fun otherNote() = otherTable().NOTE.`as`("other_note")
        fun entryDerivativesWithAlias() =
            field(select(Routines.findDerivatives(entryTable().ID))).`as`("entry_derivatives")

        fun entryDerivatives() =
            field(select(Routines.findDerivatives(entryTable().ID)))

        fun entryBasesWithAlias() = field(select(Routines.findBases(entryTable().ID))).`as`("entry_bases")
        fun entryBases() = field(select(Routines.findBases(entryTable().ID)))
        fun meaningsCount() = field(selectCount().from(Tables.MEANING)
            .where(Tables.MEANING.KASHUBIAN_ENTRY_ID.eq(entryTable().ID))).`as`("meanings_count")

        fun entryPartOfSpeechSubType() = entryTable().PART_OF_SPEECH_SUB_TYPE.`as`("entry_part_of_speech_sub_type")
        fun entryPartOfSpeech() = entryTable().PART_OF_SPEECH.`as`("entry_part_of_speech")
        fun entryNote() = entryTable().NOTE.`as`("entry_note")
        fun entryPriority() = entryTable().PRIORITY.`as`("entry_priority")
        fun entryVariation() = entryTable().VARIATION.`as`("entry_variation")
        fun entryNormalizedWord() = entryTable().NORMALIZED_WORD.`as`("entry_normalized_word")
        fun entryWord() = entryTable().WORD.`as`("entry_word")
        fun antonymMeaningEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("antonym_meaning_entry")
        fun synonymMeaningEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("synonym_meaning_entry")
        fun antonymMeaningTable() = Tables.MEANING.`as`("antonym_meaning")
        fun synonymMeaningTable() = Tables.MEANING.`as`("synonym_meaning")
        fun antonymTable() = Tables.ANTONYM.`as`("antonym")
        fun synonymTable() = Tables.SYNONYM.`as`("synonym")
        fun idiomTable() = Tables.IDIOM.`as`("idiom")
        fun exampleTable() = Tables.EXAMPLE.`as`("example")
        fun quoteTable() = Tables.QUOTE.`as`("quote")
        fun proverbTable() = Tables.PROVERB.`as`("proverb")
        fun translationTable() = Tables.TRANSLATION.`as`("translation")
        fun meaningTable() = Tables.MEANING.`as`("meaning")
        fun soundFileTable() = Tables.SOUND_FILE.`as`("sound_file")
        fun otherEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("other_entry")
        fun otherTable() = Tables.OTHER.`as`("other")
        fun meaningHyperonymEntryId() = meaningHyperonymEntryTable().ID.`as`("meaning_hyperonym_entry_id")
        fun meaningHyperonymEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("meaning_hyperonym_entry")
        fun meaningHyperonymId() = meaningHyperonymTable().ID.`as`("meaning_hyperonym_id")
        fun meaningHyperonymTable() = Tables.MEANING.`as`("meaning_hyperonym")
        fun entryBaseId() = entryBaseTable().ID.`as`("entry_base_id")
        fun entryBaseTable() = Tables.KASHUBIAN_ENTRY.`as`("entry_base")
        fun meaningHyperonymEntryWord() = meaningHyperonymEntryTable().WORD.`as`("meaning_hyperonym_entry_word")
        fun meaningHyperonymDefinition() = meaningHyperonymTable().DEFINITION.`as`("meaning_hyperonym_definition")
        fun entryBaseWord() = entryBaseTable().WORD.`as`("entry_base_word")
        fun entryTable() = Tables.KASHUBIAN_ENTRY.`as`("entry")
    }
}