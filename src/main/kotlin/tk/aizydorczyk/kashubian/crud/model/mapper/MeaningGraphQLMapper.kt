package tk.aizydorczyk.kashubian.crud.model.mapper

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jooq.Record
import org.jooq.Result
import tk.aizydorczyk.kashubian.crud.model.graphql.model.AntonymGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.ExampleGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.KashubianEntrySimplifiedGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.MeaningSimplifiedGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.PhrasalVerbGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.ProverbGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.QuoteGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.SynonymGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.TranslationGraphQL
import tk.aizydorczyk.kashubian.crud.query.graphql.base.GraphQLMapper

class MeaningGraphQLMapper : GraphQLMapper<MeaningGraphQL> {
    override fun map(results: Result<Record>): List<MeaningGraphQL> {
        val meanings = linkedMapOf<Long, MeaningGraphQL>()
        val translations = mutableMapOf<Long, TranslationGraphQL>()
        val quotes = mutableMapOf<Long, QuoteGraphQL>()
        val proverbs = mutableMapOf<Long, ProverbGraphQL>()
        val idioms = mutableMapOf<Long, PhrasalVerbGraphQL>()
        val examples = mutableMapOf<Long, ExampleGraphQL>()
        val synonyms = mutableMapOf<Long, SynonymGraphQL>()
        val antonyms = mutableMapOf<Long, AntonymGraphQL>()
        val simplifiedEntries = mutableMapOf<Long, KashubianEntrySimplifiedGraphQL>()
        val simplifiedMeanings = mutableMapOf<Long, MeaningSimplifiedGraphQL>()

        for (record in results) {
            mapHyperonym(record, simplifiedMeanings, meanings)
            mapHyperonymEntry(record, simplifiedEntries, simplifiedMeanings)
            mapMeanings(record, meanings)
            mapTranslations(record, translations, meanings)
            mapQuotes(record, quotes, meanings)
            mapProverbs(record, proverbs, meanings)
            mapIdioms(record, idioms, meanings)
            mapExamples(record, examples, meanings)
            mapSynonyms(record, synonyms, meanings)
            mapAntonyms(record, antonyms, meanings)
            mapSynonymMeanings(record, simplifiedMeanings, synonyms)
            mapAntonymMeanings(record, simplifiedMeanings, antonyms)
            mapSynonymMeaningEntries(record, simplifiedEntries, simplifiedMeanings)
            mapAntonymMeaningEntries(record, simplifiedEntries, simplifiedMeanings)
            mapKashubianEntries(record, simplifiedEntries, meanings)
        }

        return meanings.values.toList()
    }

    private fun mapKashubianEntries(
        record: Record,
        simplifyEntries: MutableMap<Long, KashubianEntrySimplifiedGraphQL>,
        meanings: LinkedHashMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "meaning_entry_id",
                simplifyEntries,
                { id ->
                    KashubianEntrySimplifiedGraphQL(
                            id = id,
                            word = record.fetchValueOrNull("meaning_entry_word",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, simplifyEntry ->
                    meaning.kashubianEntry = simplifyEntry
                })
    }

    private fun mapHyperonym(record: Record,
        simplifiedMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "meaning_hyperonym_id",
                simplifiedMeanings,
                { id ->
                    MeaningSimplifiedGraphQL(
                            id = id,
                            definition = record.fetchValueOrNull("meaning_hyperonym_definition",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, hyperonym ->
                    meaning.hyperonym = hyperonym
                })
    }

    private fun mapHyperonymEntry(record: Record,
        simplifyEntries: MutableMap<Long, KashubianEntrySimplifiedGraphQL>,
        simplifyMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>) {
        record.mapAndAssignById(
                "meaning_hyperonym_entry_id",
                simplifyEntries,
                { id ->
                    KashubianEntrySimplifiedGraphQL(
                            id = id,
                            word = record.fetchValueOrNull("meaning_hyperonym_entry_word",
                                    String::class.java))
                },
                "meaning_hyperonym_id",
                simplifyMeanings,
                { simplifyMeaning, simplifyEntry ->
                    simplifyMeaning.kashubianEntry = simplifyEntry
                })
    }

    private fun mapExamples(record: Record,
        examples: MutableMap<Long, ExampleGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "example_id",
                examples,
                { id ->
                    ExampleGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("example_note",
                                    String::class.java),
                            example = record.fetchValueOrNull("example_example",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, example ->
                    meaning.examples.add(example)
                })
    }

    private fun mapIdioms(record: Record,
        idioms: MutableMap<Long, PhrasalVerbGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "idiom_id",
                idioms,
                { id ->
                    PhrasalVerbGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("idiom_note",
                                    String::class.java),
                            idiom = record.fetchValueOrNull("idiom_idiom",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, idiom ->
                    meaning.idioms.add(idiom)
                })
    }

    private fun mapProverbs(record: Record,
        proverbs: MutableMap<Long, ProverbGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "proverb_id",
                proverbs,
                { id ->
                    ProverbGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("proverb_note",
                                    String::class.java),
                            proverb = record.fetchValueOrNull("proverb_proverb",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, proverb ->
                    meaning.proverbs.add(proverb)
                })
    }

    private fun mapQuotes(record: Record,
        quotes: MutableMap<Long, QuoteGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "quote_id",
                quotes,
                { id ->
                    QuoteGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("quote_note",
                                    String::class.java),
                            quote = record.fetchValueOrNull("quote_quote",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, quote ->
                    meaning.quotes.add(quote)
                })
    }

    private fun mapTranslations(record: Record,
        translations: MutableMap<Long, TranslationGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "translation_id",
                translations,
                { id ->
                    TranslationGraphQL(
                            id = id,
                            polish =
                            record.fetchValueOrNull("translation_polish",
                                    String::class.java),
                            normalizedPolish =
                            record.fetchValueOrNull("translation_normalized_polish",
                                    String::class.java),
                            english =
                            record.fetchValueOrNull("translation_english",
                                    String::class.java),
                            normalizedEnglish =
                            record.fetchValueOrNull("translation_normalized_english",
                                    String::class.java),
                            ukrainian =
                            record.fetchValueOrNull("translation_ukrainian",
                                    String::class.java),
                            normalizedUkrainian =
                            record.fetchValueOrNull("translation_normalized_ukrainian",
                                    String::class.java),
                            german =
                            record.fetchValueOrNull("translation_german",
                                    String::class.java),
                            normalizedGerman =
                            record.fetchValueOrNull("translation_normalized_german",
                                    String::class.java)
                    )
                },
                "meaning_id",
                meanings,
                { meaning, translation ->
                    meaning.translation = translation
                })
    }

    private fun mapMeanings(record: Record,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById<MeaningGraphQL, Unit>(
                "meaning_id",
                meanings,
                { id ->
                    MeaningGraphQL(
                            id = id,
                            definition =
                            record.fetchValueOrNull("meaning_definition",
                                    String::class.java),
                            origin =
                            record.fetchValueOrNull("meaning_origin",
                                    String::class.java),
                            hyperonyms =
                            record.fetchValueOrNull("meaning_hyperonyms",
                                    ArrayNode::class.java) ?: jacksonObjectMapper().createArrayNode(),
                            hyponyms =
                            record.fetchValueOrNull("meaning_hyponyms",
                                    ArrayNode::class.java) ?: jacksonObjectMapper().createArrayNode())
                })
    }

    private fun mapAntonyms(record: Record,
        antonyms: MutableMap<Long, AntonymGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "antonym_id",
                antonyms,
                { id ->
                    AntonymGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("antonym_note",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, antonym ->
                    meaning.antonyms.add(antonym)
                })
    }

    private fun mapSynonyms(record: Record,
        synonyms: MutableMap<Long, SynonymGraphQL>,
        meanings: MutableMap<Long, MeaningGraphQL>) {
        record.mapAndAssignById(
                "synonym_id",
                synonyms,
                { id ->
                    SynonymGraphQL(
                            id = id,
                            note = record.fetchValueOrNull("synonym_note",
                                    String::class.java))
                },
                "meaning_id",
                meanings,
                { meaning, synonym ->
                    meaning.synonyms.add(synonym)
                })
    }

    private fun mapSynonymMeanings(record: Record,
        simplifyMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>,
        synonyms: MutableMap<Long, SynonymGraphQL>) {
        record.mapAndAssignById(
                "synonym_meaning_id",
                simplifyMeanings,
                { id ->
                    MeaningSimplifiedGraphQL(
                            id = id,
                            definition = record.fetchValueOrNull("synonym_meaning_definition",
                                    String::class.java))
                },
                "synonym_id",
                synonyms,
                { synonym, simplifyMeaning ->
                    synonym.synonym = simplifyMeaning
                })
    }

    private fun mapAntonymMeanings(record: Record,
        simplifyMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>,
        antonyms: MutableMap<Long, AntonymGraphQL>) {
        record.mapAndAssignById(
                "antonym_meaning_id",
                simplifyMeanings,
                { id ->
                    MeaningSimplifiedGraphQL(
                            id = id,
                            definition = record.fetchValueOrNull("antonym_meaning_definition",
                                    String::class.java))
                },
                "antonym_id",
                antonyms,
                { antonym, simplifyMeaning ->
                    antonym.antonym = simplifyMeaning
                })
    }

    private fun mapSynonymMeaningEntries(record: Record,
        simplifyEntries: MutableMap<Long, KashubianEntrySimplifiedGraphQL>,
        simplifyMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>) {
        record.mapAndAssignById(
                "synonym_meaning_entry_id",
                simplifyEntries,
                { id ->
                    KashubianEntrySimplifiedGraphQL(
                            id = id,
                            word = record.fetchValueOrNull("synonym_meaning_entry_word",
                                    String::class.java))
                },
                "synonym_meaning_id",
                simplifyMeanings,
                { simplifyMeaning, simplifyEntry ->
                    simplifyMeaning.kashubianEntry = simplifyEntry
                })
    }

    private fun mapAntonymMeaningEntries(record: Record,
        simplifyEntries: MutableMap<Long, KashubianEntrySimplifiedGraphQL>,
        simplifyMeanings: MutableMap<Long, MeaningSimplifiedGraphQL>) {
        record.mapAndAssignById(
                "antonym_meaning_entry_id",
                simplifyEntries,
                { id ->
                    KashubianEntrySimplifiedGraphQL(
                            id = id,
                            word = record.fetchValueOrNull("antonym_meaning_entry_word",
                                    String::class.java))
                },
                "antonym_meaning_id",
                simplifyMeanings,
                { simplifyMeaning, simplifyEntry ->
                    simplifyMeaning.kashubianEntry = simplifyEntry
                })
    }

    private fun <T> Record.fetchValueOrNull(fieldName: String, type: Class<T>) =
        if (this.indexOf(fieldName) >= 0) {
            this.get(fieldName, type)
        } else null

    private fun <CreatedType, AssignType> Record.mapAndAssignById(
        createIdFieldName: String,
        createMap: MutableMap<Long, CreatedType>,
        createFunction: (Long) -> CreatedType,
        assignIdFieldName: String = "",
        assignMap: MutableMap<Long, AssignType> = mutableMapOf(),
        assignFunction: (AssignType, CreatedType) -> Unit = { _: AssignType, _: CreatedType -> }) {
        if (this.indexOf(createIdFieldName) >= 0) {
            this.fetchValueOrNull(createIdFieldName, Long::class.javaObjectType)?.let { idInRecord ->
                val createdOrCollectedValue = createMap.computeIfAbsent(idInRecord, createFunction)
                assignMap[this.fetchValueOrNull(assignIdFieldName, Long::class.java)]?.let {
                    assignFunction(it, createdOrCollectedValue)
                }
            }
        }
    }
}