package tk.aizydorczyk.kashubian.crud.query

import org.jooq.impl.DSL
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Routines
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables

internal object KashubianEntryQueryRelations {
    internal val FIELD_TO_JOIN_RELATIONS = mapOf(
            "KashubianEntryPaged.select/KashubianEntry.others" to
                    Triple(otherTable(),
                            entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                            otherId()),
            "KashubianEntryPaged.select/KashubianEntry.soundFile" to
                    Triple(soundFileTable(),
                            entryTable().ID.eq(soundFileTable().KASHUBIAN_ENTRY_ID),
                            soundFileId()),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.other" to
                    Triple(otherEntryTable(),
                            otherTable().OTHER_ID.`as`("other_id").eq(otherEntryTable().ID),
                            otherEntryId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings" to
                    Triple(meaningTable(),
                            entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                            meaningId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation" to
                    Triple(translationTable(),
                            meaningTable().ID.eq(translationTable().MEANING_ID),
                            translationId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.proverbs" to
                    Triple(proverbTable(),
                            meaningTable().ID.eq(proverbTable().MEANING_ID),
                            proverbId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.quotes" to
                    Triple(quoteTable(),
                            meaningTable().ID.eq(quoteTable().MEANING_ID),
                            quoteId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.examples" to
                    Triple(exampleTable(),
                            meaningTable().ID.eq(exampleTable().MEANING_ID),
                            exampleId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.phrasalVerbs" to
                    Triple(phrasalVerbTable(),
                            meaningTable().ID.eq(phrasalVerbTable().MEANING_ID),
                            phrasalVerbId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms" to
                    Triple(synonymTable(),
                            meaningTable().ID.eq(synonymTable().MEANING_ID),
                            synonymId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms" to
                    Triple(antonymTable(),
                            meaningTable().ID.eq(antonymTable().MEANING_ID),
                            antonymId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms/Synonym.synonym" to
                    Triple(synonymMeaningTable(),
                            synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                            synonymMeaningId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms/Antonym.antonym" to
                    Triple(antonymMeaningTable(),
                            antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                            antonymMeaningId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms/Synonym.synonym/MeaningSimplified.kashubianEntry" to
                    Triple(synonymMeaningEntryTable(),
                            synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(synonymMeaningEntryTable().ID),
                            synonymMeaningEntryId()),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms/Antonym.antonym/MeaningSimplified.kashubianEntry" to
                    Triple(antonymMeaningEntryTable(),
                            antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(antonymMeaningEntryTable().ID),
                            antonymMeaningEntryId())
    )

    internal val FIELD_TO_COLUMN_RELATIONS = mapOf(
            "KashubianEntryPaged.select/KashubianEntry.word" to
                    entryWord(),
            "KashubianEntryPaged.select/KashubianEntry.normalizedWord" to
                    entryNormalizedWord(),
            "KashubianEntryPaged.select/KashubianEntry.variation" to
                    entryVariation(),
            "KashubianEntryPaged.select/KashubianEntry.priority" to
                    entryPriority(),
            "KashubianEntryPaged.select/KashubianEntry.note" to
                    entryNote(),
            "KashubianEntryPaged.select/KashubianEntry.partOfSpeech" to
                    entryPartOfSpeech(),
            "KashubianEntryPaged.select/KashubianEntry.partOfSpeechSubType" to
                    entryPartOfSpeechSubType(),
            "KashubianEntryPaged.select/KashubianEntry.meaningsCount" to
                    meaningsCount(),
            "KashubianEntryPaged.select/KashubianEntry.bases" to
                    entryBases(),
            "KashubianEntryPaged.select/KashubianEntry.derivatives" to
                    entryDerivatives(),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.note" to
                    otherNote(),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.other/KashubianEntrySimplified.word" to
                    otherEntryWord(),
            "KashubianEntryPaged.select/KashubianEntry.soundFile/SoundFile.type" to
                    soundFileType(),
            "KashubianEntryPaged.select/KashubianEntry.soundFile/SoundFile.fileName" to
                    soundFileFileName(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.origin" to
                    meaningOrigin(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.definition" to
                    meaningDefinition(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyperonyms" to
                    meaningHyperonyms(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyponyms" to
                    meaningHyponyms(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.polish" to
                    translationPolish(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedPolish" to
                    translationPolishNormalizedWord(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.english" to
                    translationEnglish(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedEnglish" to
                    translationNormalizedEnglish(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.german" to
                    translationGerman(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedGerman" to
                    translationNormalizedGerman(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.ukrainian" to
                    translationUkrainian(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedUkrainian" to
                    translationNormalizedUkrainian(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.proverbs/Proverb.note" to
                    proverbNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.proverbs/Proverb.proverb" to
                    proverbProverb(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.quotes/Quote.note" to
                    quoteNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.quotes/Quote.quote" to
                    quoteQuote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.examples/Example.note" to
                    exampleNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.examples/Example.example" to
                    exampleExample(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.phrasalVerbs/PhrasalVerb.note" to
                    phrasalVerbNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.phrasalVerbs/PhrasalVerb.phrasalVerb" to
                    phrasalVerbPhrasalVerb(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms/Synonym.note" to
                    synonymNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms/Antonym.note" to
                    antonymNote(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms/Synonym.synonym/MeaningSimplified.definition" to
                    synonymMeaningDefinition(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms/Antonym.antonym/MeaningSimplified.definition" to
                    antonymMeaningDefinition(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.synonyms/Synonym.synonym/MeaningSimplified.kashubianEntry/KashubianEntrySimplified.word" to
                    synonymMeaningEntryWord(),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.antonyms/Antonym.antonym/MeaningSimplified.kashubianEntry/KashubianEntrySimplified.word" to
                    antonymMeaningEntryWord()
    )


    internal val CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN =
        listOf(
                "entry.id" to Pair(entryTable().ID, emptyList()),
                "entry.note" to Pair(entryTable().NOTE, emptyList()),
                "entry.word" to Pair(entryTable().WORD, emptyList()),
                "entry.normalizedWord" to Pair(entryTable().NORMALIZED_WORD, emptyList()),
                "entry.priority" to Pair(entryTable().PRIORITY, emptyList()),
                "entry.soundFile.id" to Pair(soundFileTable().ID, emptyList()),
                "entry.soundFile.type" to Pair(soundFileTable().TYPE, emptyList()),
                "entry.soundFile.fileName" to Pair(soundFileTable().FILE_NAME, emptyList()),
                "entry.others.id" to Pair(
                        otherTable().ID,
                        listOf(otherTable() to entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID))),
                "entry.others.note" to Pair(
                        otherTable().NOTE,
                        listOf(otherTable() to entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID))),
                "entry.others.other.id" to Pair(otherEntryTable().ID,
                        listOf(otherTable() to entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                                otherEntryTable() to otherTable().OTHER_ID.`as`("other_id").eq(otherEntryTable().ID))),
                "entry.others.other.word" to Pair(otherEntryTable().WORD,
                        listOf(otherTable() to entryTable().ID.eq(otherTable().KASHUBIAN_ENTRY_ID),
                                otherEntryTable() to otherTable().OTHER_ID.`as`("other_id").eq(otherEntryTable().ID))),
                "entry.meanings.id" to Pair(meaningTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "entry.meanings.origin" to Pair(meaningTable().ORIGIN,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "entry.meanings.definition" to Pair(meaningTable().DEFINITION,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID))),
                "entry.meanings.synonyms.id" to Pair(synonymTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "entry.meanings.synonyms.note" to Pair(synonymTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "entry.meanings.synonyms.synonym.id" to Pair(synonymMeaningTable(),
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() to synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "entry.meanings.synonyms.synonym.definition" to Pair(synonymMeaningTable().DEFINITION,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() to synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "entry.meanings.synonyms.synonym.kashubianEntry.id" to Pair(synonymMeaningEntryTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() to synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() to synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "entry.meanings.synonyms.synonym.kashubianEntry.word" to Pair(synonymMeaningEntryTable().WORD,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                synonymTable() to meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() to synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() to synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "entry.meanings.proverbs.id" to Pair(proverbTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() to meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "entry.meanings.proverbs.note" to Pair(proverbTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() to meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "entry.meanings.proverbs.proverb" to Pair(proverbTable().PROVERB_,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                proverbTable() to meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "entry.meanings.translation.id" to Pair(translationTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.polish" to Pair(translationTable().POLISH,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.normalizedPolish" to Pair(translationTable().NORMALIZED_POLISH,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.english" to Pair(translationTable().ENGLISH,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.normalizedEnglish" to Pair(translationTable().NORMALIZED_ENGLISH,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.german" to Pair(translationTable().GERMAN,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.normalizedGerman" to Pair(translationTable().NORMALIZED_GERMAN,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.ukrainian" to Pair(translationTable().UKRAINIAN,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.translation.normalizedUkrainian" to Pair(translationTable().NORMALIZED_UKRAINIAN,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                translationTable() to meaningTable().ID.eq(translationTable().MEANING_ID))),
                "entry.meanings.quotes.id" to Pair(quoteTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() to meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "entry.meanings.quotes.note" to Pair(quoteTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() to meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "entry.meanings.quotes.quote" to Pair(quoteTable().QUOTE_,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                quoteTable() to meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "entry.meanings.antonyms.id" to Pair(antonymTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "entry.meanings.antonyms.note" to Pair(antonymTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "entry.meanings.antonyms.antonym.id" to Pair(antonymMeaningTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() to antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "entry.meanings.antonyms.antonym.definition" to Pair(antonymMeaningTable().DEFINITION,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() to antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "entry.meanings.antonyms.antonym.kashubianEntry.id" to Pair(antonymMeaningEntryTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() to antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() to antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "entry.meanings.antonyms.antonym.kashubianEntry.word" to Pair(antonymMeaningEntryTable().WORD,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                antonymTable() to meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() to antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() to antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "entry.meanings.examples.id" to Pair(exampleTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() to meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "entry.meanings.examples.note" to Pair(exampleTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() to meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "entry.meanings.examples.example" to Pair(exampleTable().EXAMPLE_,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                exampleTable() to meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "entry.meanings.phrasalVerbs.id" to Pair(phrasalVerbTable().ID,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() to meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "entry.meanings.phrasalVerbs.note" to Pair(phrasalVerbTable().NOTE,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() to meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "entry.meanings.phrasalVerbs.phrasalVerb" to Pair(phrasalVerbTable().PHRASAL_VERB_,
                        listOf(meaningTable() to entryTable().ID.eq(meaningTable().KASHUBIAN_ENTRY_ID),
                                phrasalVerbTable() to meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
        ).map { criteriaAndField ->
            listOf(".EQ", ".LIKE_", ".LIKE").map {
                Pair(criteriaAndField.first + it,
                        Pair(criteriaAndField.second.first, criteriaAndField.second.second))
            }
        }.flatten().associate { it.first to it.second }

    private fun antonymMeaningEntryId() = antonymMeaningEntryTable().ID.`as`("antonym_meaning_entry_id")

    private fun synonymMeaningEntryId() = synonymMeaningEntryTable().ID.`as`("synonym_meaning_entry_id")

    private fun antonymMeaningId() = antonymMeaningTable().ID.`as`("antonym_meaning_id")

    private fun synonymMeaningId() = synonymMeaningTable().ID.`as`("synonym_meaning_id")

    private fun antonymId() = antonymTable().ID.`as`("antonym_id")

    private fun synonymId() = synonymTable().ID.`as`("synonym_id")

    private fun phrasalVerbId() = phrasalVerbTable().ID.`as`("phrasal_verb_id")

    private fun exampleId() = exampleTable().ID.`as`("example_id")

    private fun quoteId() = quoteTable().ID.`as`("quote_id")

    private fun proverbId() = proverbTable().ID.`as`("proverb_id")

    private fun translationId() = translationTable().ID.`as`("translation_id")

    private fun meaningId() = meaningTable().ID.`as`("meaning_id")

    private fun otherEntryId() = otherEntryTable().ID.`as`("other_entry_id")

    private fun soundFileId() = soundFileTable().ID.`as`("sound_file_id")

    private fun otherId() = otherTable().ID.`as`("other_id")

    private fun antonymMeaningEntryWord() = antonymMeaningEntryTable().WORD.`as`("antonym_meaning_entry_word")

    private fun synonymMeaningEntryWord() = synonymMeaningEntryTable().WORD.`as`("synonym_meaning_entry_word")

    private fun antonymMeaningDefinition() = antonymMeaningTable().DEFINITION.`as`("antonym_meaning_definition")

    private fun synonymMeaningDefinition() = synonymMeaningTable().DEFINITION.`as`("synonym_meaning_definition")

    private fun antonymNote() = antonymTable().NOTE.`as`("antonym_note")

    private fun synonymNote() = synonymTable().NOTE.`as`("synonym_note")

    private fun phrasalVerbPhrasalVerb() = phrasalVerbTable().PHRASAL_VERB_.`as`("phrasal_verb_phrasal_verb")

    private fun phrasalVerbNote() = phrasalVerbTable().NOTE.`as`("phrasal_verb_note")

    private fun exampleExample() = exampleTable().EXAMPLE_.`as`("example_example")

    private fun exampleNote() = exampleTable().NOTE.`as`("example_note")

    private fun quoteQuote() = quoteTable().QUOTE_.`as`("quote_quote")

    private fun quoteNote() = quoteTable().NOTE.`as`("quote_note")

    private fun proverbProverb() = proverbTable().PROVERB_.`as`("proverb_proverb")

    private fun proverbNote() = proverbTable().NOTE.`as`("proverb_note")

    private fun translationNormalizedUkrainian() =
        translationTable().NORMALIZED_UKRAINIAN.`as`("translation_normalized_ukrainian")

    private fun translationUkrainian() = translationTable().UKRAINIAN.`as`("translation_ukrainian")

    private fun translationNormalizedGerman() =
        translationTable().NORMALIZED_GERMAN.`as`("translation_normalized_german")

    private fun translationGerman() = translationTable().GERMAN.`as`("translation_german")

    private fun translationNormalizedEnglish() =
        translationTable().NORMALIZED_ENGLISH.`as`("translation_normalized_english")

    private fun translationEnglish() = translationTable().ENGLISH.`as`("translation_english")

    private fun translationPolishNormalizedWord() =
        translationTable().NORMALIZED_POLISH.`as`("translation_normalized_polish")

    private fun meaningDefinition() = meaningTable().DEFINITION.`as`("meaning_definition")

    private fun meaningOrigin() = meaningTable().ORIGIN.`as`("meaning_origin")

    private fun translationPolish() = translationTable().POLISH.`as`("translation_polish")

    private fun meaningHyponyms() =
        DSL.field(DSL.select(Routines.findHyponyms(meaningTable().ID))).`as`("meaning_hyponyms")

    private fun meaningHyperonyms() =
        DSL.field(DSL.select(Routines.findHyperonyms(meaningTable().ID))).`as`("meaning_hyperonyms")

    private fun soundFileFileName() = soundFileTable().FILE_NAME.`as`("sound_file_file_name")

    private fun soundFileType() = soundFileTable().TYPE.`as`("sound_file_type")

    private fun otherEntryWord() = otherEntryTable().WORD.`as`("other_entry_word")

    private fun otherNote() = otherTable().NOTE.`as`("other_note")

    private fun entryDerivatives() =
        DSL.field(DSL.select(Routines.findDerivatives(entryTable().ID))).`as`("entry_derivatives")

    private fun entryBases() = DSL.field(DSL.select(Routines.findBases(entryTable().ID))).`as`("entry_bases")

    private fun meaningsCount() = DSL.field(DSL.selectCount().from(Tables.MEANING)
        .where(Tables.MEANING.KASHUBIAN_ENTRY_ID.eq(entryTable().ID))).`as`("meanings_count")

    private fun entryPartOfSpeechSubType() = entryTable().PART_OF_SPEECH_SUB_TYPE.`as`("entry_part_of_speech_sub_type")

    private fun entryPartOfSpeech() = entryTable().PART_OF_SPEECH.`as`("entry_part_of_speech")

    private fun entryNote() = entryTable().NOTE.`as`("entry_note")

    private fun entryPriority() = entryTable().PRIORITY.`as`("entry_priority")

    private fun entryVariation() = entryTable().VARIATION.`as`("entry_variation")

    private fun entryNormalizedWord() = entryTable().NORMALIZED_WORD.`as`("entry_normalized_word")

    private fun entryWord() = entryTable().WORD.`as`("entry_word")

    private fun antonymMeaningEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("antonym_meaning_entry")

    private fun synonymMeaningEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("synonym_meaning_entry")

    private fun antonymMeaningTable() = Tables.MEANING.`as`("antonym_meaning")

    private fun synonymMeaningTable() = Tables.MEANING.`as`("synonym_meaning")

    private fun antonymTable() = Tables.ANTONYM.`as`("antonym")

    private fun synonymTable() = Tables.SYNONYM.`as`("synonym")

    private fun phrasalVerbTable() = Tables.PHRASAL_VERB.`as`("phrasal_verb")

    private fun exampleTable() = Tables.EXAMPLE.`as`("example")

    private fun quoteTable() = Tables.QUOTE.`as`("quote")

    private fun proverbTable() = Tables.PROVERB.`as`("proverb")

    private fun translationTable() = Tables.TRANSLATION.`as`("translation")

    private fun meaningTable() = Tables.MEANING.`as`("meaning")

    private fun soundFileTable() = Tables.SOUND_FILE.`as`("sound_file")

    private fun otherEntryTable() = Tables.KASHUBIAN_ENTRY.`as`("other_entry")

    private fun otherTable() = Tables.OTHER.`as`("other")

    private fun entryTable() = Tables.KASHUBIAN_ENTRY.`as`("entry")

}