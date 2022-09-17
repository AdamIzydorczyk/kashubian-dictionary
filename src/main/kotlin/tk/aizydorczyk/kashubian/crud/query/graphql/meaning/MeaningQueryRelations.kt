package tk.aizydorczyk.kashubian.crud.query.graphql.meaning

import org.jooq.impl.DSL
import tk.aizydorczyk.kashubian.crud.extension.fieldPath
import tk.aizydorczyk.kashubian.crud.extension.fieldWithJoins
import tk.aizydorczyk.kashubian.crud.extension.joinedBy
import tk.aizydorczyk.kashubian.crud.extension.on
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Routines
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables

object MeaningQueryRelations {
    internal val FIND_ALL_FIELD_TO_JOIN_RELATIONS = mapOf(
            "MeaningsPaged.select/Meaning.translation" to
                    Triple(translationTable(),
                            meaningTable().ID.eq(translationTable().MEANING_ID),
                            translationId()),
            "MeaningsPaged.select/Meaning.proverbs" to
                    Triple(proverbTable(),
                            meaningTable().ID.eq(proverbTable().MEANING_ID),
                            proverbId()),
            "MeaningsPaged.select/Meaning.quotes" to
                    Triple(quoteTable(),
                            meaningTable().ID.eq(quoteTable().MEANING_ID),
                            quoteId()),
            "MeaningsPaged.select/Meaning.examples" to
                    Triple(exampleTable(),
                            meaningTable().ID.eq(exampleTable().MEANING_ID),
                            exampleId()),
            "MeaningsPaged.select/Meaning.phrasalVerbs" to
                    Triple(phrasalVerbTable(),
                            meaningTable().ID.eq(phrasalVerbTable().MEANING_ID),
                            phrasalVerbId()),
            "MeaningsPaged.select/Meaning.synonyms" to
                    Triple(synonymTable(),
                            meaningTable().ID.eq(synonymTable().MEANING_ID),
                            synonymId()),
            "MeaningsPaged.select/Meaning.antonyms" to
                    Triple(antonymTable(),
                            meaningTable().ID.eq(antonymTable().MEANING_ID),
                            antonymId()),
            "MeaningsPaged.select/Meaning.synonyms/Synonym.synonym" to
                    Triple(synonymMeaningTable(),
                            synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                            synonymMeaningId()),
            "MeaningsPaged.select/Meaning.antonyms/Antonym.antonym" to
                    Triple(antonymMeaningTable(),
                            antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                            antonymMeaningId()),
            "MeaningsPaged.select/Meaning.synonyms/Synonym.synonym/MeaningSimplified.kashubianEntry" to
                    Triple(synonymMeaningEntryTable(),
                            synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(synonymMeaningEntryTable().ID),
                            synonymMeaningEntryId()),
            "MeaningsPaged.select/Meaning.antonyms/Antonym.antonym/MeaningSimplified.kashubianEntry" to
                    Triple(antonymMeaningEntryTable(),
                            antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(antonymMeaningEntryTable().ID),
                            antonymMeaningEntryId())
    )

    internal val FIND_ONE_FIELD_TO_JOIN_RELATIONS = FIND_ALL_FIELD_TO_JOIN_RELATIONS.mapKeys {
        it.key.removePrefix("MeaningsPaged.select/")
    }

    internal val FIND_ALL_FIELD_TO_COLUMN_RELATIONS = mapOf(
            "MeaningsPaged.select/Meaning.origin" to
                    meaningOrigin(),
            "MeaningsPaged.select/Meaning.definition" to
                    meaningDefinition(),
            "MeaningsPaged.select/Meaning.hyperonyms" to
                    meaningHyperonyms(),
            "MeaningsPaged.select/Meaning.hyponyms" to
                    meaningHyponyms(),
            "MeaningsPaged.select/Meaning.translation/Translation.polish" to
                    translationPolish(),
            "MeaningsPaged.select/Meaning.translation/Translation.normalizedPolish" to
                    translationPolishNormalizedWord(),
            "MeaningsPaged.select/Meaning.translation/Translation.english" to
                    translationEnglish(),
            "MeaningsPaged.select/Meaning.translation/Translation.normalizedEnglish" to
                    translationNormalizedEnglish(),
            "MeaningsPaged.select/Meaning.translation/Translation.german" to
                    translationGerman(),
            "MeaningsPaged.select/Meaning.translation/Translation.normalizedGerman" to
                    translationNormalizedGerman(),
            "MeaningsPaged.select/Meaning.translation/Translation.ukrainian" to
                    translationUkrainian(),
            "MeaningsPaged.select/Meaning.translation/Translation.normalizedUkrainian" to
                    translationNormalizedUkrainian(),
            "MeaningsPaged.select/Meaning.proverbs/Proverb.note" to
                    proverbNote(),
            "MeaningsPaged.select/Meaning.proverbs/Proverb.proverb" to
                    proverbProverb(),
            "MeaningsPaged.select/Meaning.quotes/Quote.note" to
                    quoteNote(),
            "MeaningsPaged.select/Meaning.quotes/Quote.quote" to
                    quoteQuote(),
            "MeaningsPaged.select/Meaning.examples/Example.note" to
                    exampleNote(),
            "MeaningsPaged.select/Meaning.examples/Example.example" to
                    exampleExample(),
            "MeaningsPaged.select/Meaning.phrasalVerbs/PhrasalVerb.note" to
                    phrasalVerbNote(),
            "MeaningsPaged.select/Meaning.phrasalVerbs/PhrasalVerb.phrasalVerb" to
                    phrasalVerbPhrasalVerb(),
            "MeaningsPaged.select/Meaning.synonyms/Synonym.note" to
                    synonymNote(),
            "MeaningsPaged.select/Meaning.antonyms/Antonym.note" to
                    antonymNote(),
            "MeaningsPaged.select/Meaning.synonyms/Synonym.synonym/MeaningSimplified.definition" to
                    synonymMeaningDefinition(),
            "MeaningsPaged.select/Meaning.antonyms/Antonym.antonym/MeaningSimplified.definition" to
                    antonymMeaningDefinition(),
            "MeaningsPaged.select/Meaning.synonyms/Synonym.synonym/MeaningSimplified.kashubianEntry/KashubianEntrySimplified.word" to
                    synonymMeaningEntryWord(),
            "MeaningsPaged.select/Meaning.antonyms/Antonym.antonym/MeaningSimplified.kashubianEntry/KashubianEntrySimplified.word" to
                    antonymMeaningEntryWord()
    )

    internal val FIND_ONE_FIELD_TO_COLUMN_RELATIONS = FIND_ALL_FIELD_TO_COLUMN_RELATIONS.mapKeys {
        it.key.removePrefix("MeaningsPaged.select/")
    }

    val CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN =
        listOf(
                "select.id" to (meaningTable().ID joinedBy
                        emptyList()),
                "select.origin" to (meaningTable().ORIGIN joinedBy emptyList()),
                "select.definition" to (meaningTable().DEFINITION joinedBy emptyList()),
                "select.synonyms.id" to (synonymTable().ID joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "select.synonyms.note" to (synonymTable().NOTE joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID))),
                "select.synonyms.synonym.id" to (synonymMeaningTable() joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "select.synonyms.synonym.definition" to (synonymMeaningTable().DEFINITION joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID))),
                "select.synonyms.synonym.kashubianEntry.id" to (synonymMeaningEntryTable().ID joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "select.synonyms.synonym.kashubianEntry.word" to (synonymMeaningEntryTable().WORD joinedBy
                        listOf(
                                synonymTable() on meaningTable().ID.eq(synonymTable().MEANING_ID),
                                synonymMeaningTable() on synonymTable().MEANING_ID.eq(synonymMeaningTable().ID),
                                synonymMeaningEntryTable() on synonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        synonymMeaningEntryTable().ID))),
                "select.proverbs.id" to (proverbTable().ID joinedBy
                        listOf(
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "select.proverbs.note" to (proverbTable().NOTE joinedBy
                        listOf(
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "select.proverbs.proverb" to (proverbTable().PROVERB_ joinedBy
                        listOf(
                                proverbTable() on meaningTable().ID.eq(proverbTable().MEANING_ID))),
                "select.translation.id" to (translationTable().ID joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.polish" to (translationTable().POLISH joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.normalizedPolish" to (translationTable().NORMALIZED_POLISH joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.english" to (translationTable().ENGLISH joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.normalizedEnglish" to (translationTable().NORMALIZED_ENGLISH joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.german" to (translationTable().GERMAN joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.normalizedGerman" to (translationTable().NORMALIZED_GERMAN joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.ukrainian" to (translationTable().UKRAINIAN joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.translation.normalizedUkrainian" to (translationTable().NORMALIZED_UKRAINIAN joinedBy
                        listOf(
                                translationTable() on meaningTable().ID.eq(translationTable().MEANING_ID))),
                "select.quotes.id" to (quoteTable().ID joinedBy
                        listOf(
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "select.quotes.note" to (quoteTable().NOTE joinedBy
                        listOf(
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "select.quotes.quote" to (quoteTable().QUOTE_ joinedBy
                        listOf(
                                quoteTable() on meaningTable().ID.eq(quoteTable().MEANING_ID))),
                "select.antonyms.id" to (antonymTable().ID joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "select.antonyms.note" to (antonymTable().NOTE joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID))),
                "select.antonyms.antonym.id" to (antonymMeaningTable().ID joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "select.antonyms.antonym.definition" to (antonymMeaningTable().DEFINITION joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID))),
                "select.antonyms.antonym.kashubianEntry.id" to (antonymMeaningEntryTable().ID joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "select.antonyms.antonym.kashubianEntry.word" to (antonymMeaningEntryTable().WORD joinedBy
                        listOf(
                                antonymTable() on meaningTable().ID.eq(antonymTable().MEANING_ID),
                                antonymMeaningTable() on antonymTable().MEANING_ID.eq(antonymMeaningTable().ID),
                                antonymMeaningEntryTable() on antonymMeaningTable().KASHUBIAN_ENTRY_ID.eq(
                                        antonymMeaningEntryTable().ID))),
                "select.examples.id" to (exampleTable().ID joinedBy
                        listOf(
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "select.examples.note" to (exampleTable().NOTE joinedBy
                        listOf(
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "select.examples.example" to (exampleTable().EXAMPLE_ joinedBy
                        listOf(
                                exampleTable() on meaningTable().ID.eq(exampleTable().MEANING_ID))),
                "select.phrasalVerbs.id" to (phrasalVerbTable().ID joinedBy
                        listOf(
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "select.phrasalVerbs.note" to (phrasalVerbTable().NOTE joinedBy
                        listOf(
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID))),
                "select.phrasalVerbs.phrasalVerb" to (phrasalVerbTable().PHRASAL_VERB_ joinedBy
                        listOf(
                                phrasalVerbTable() on meaningTable().ID.eq(phrasalVerbTable().MEANING_ID)))
        ).map { criteriaAndField ->
            listOf(".EQ", ".LIKE_", ".LIKE", ".BY_NORMALIZED").map {
                criteriaAndField.fieldPath() + it to
                        (criteriaAndField.fieldWithJoins().field to criteriaAndField.fieldWithJoins().joins)
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

    fun meaningId() = Tables.MEANING.`as`("meaning").ID.`as`("meaning_id")

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

    fun meaningTable() = Tables.MEANING.`as`("meaning")

}
