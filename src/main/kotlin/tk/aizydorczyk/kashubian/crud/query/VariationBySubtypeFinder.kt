package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.jeasy.random.EasyRandom
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.json.AdjectivePronounVariation
import tk.aizydorczyk.kashubian.crud.model.json.AdjectiveVariation
import tk.aizydorczyk.kashubian.crud.model.json.AdverbVariation
import tk.aizydorczyk.kashubian.crud.model.json.NounPronounVariation
import tk.aizydorczyk.kashubian.crud.model.json.NounVariation
import tk.aizydorczyk.kashubian.crud.model.json.NumeralVariation
import tk.aizydorczyk.kashubian.crud.model.json.VerbVariation
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType

@Component
class VariationBySubtypeFinder(val exampleVariationsGenerator: ExampleVariationsGenerator) {
    fun find(subType: PartOfSpeechSubType): ObjectNode? {
        val exampleBySubtype: Map<String, ObjectNode?> = exampleVariationsGenerator.exampleBySubtype()
        return exampleBySubtype[subType.name]
    }
}

@Component
class ExampleVariationsGenerator(val objectMapper: ObjectMapper) {
    private final val variations: Array<Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>>
    private final val exampleBySubtype: Map<String, ObjectNode?>

    init {
        val variationsGenerator = EasyRandom()
        variations = listOf(
                variationsGenerator.nextObject(NounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.NEUTER, PartOfSpeechType.NOUN) },
                variationsGenerator.nextObject(NounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.MASCULINE, PartOfSpeechType.NOUN) },
                variationsGenerator.nextObject(NounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.FEMININE, PartOfSpeechType.NOUN) },
                variationsGenerator.nextObject(NounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.PLURAL_MASCULINE, PartOfSpeechType.NOUN) },
                variationsGenerator.nextObject(NounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.NON_MASCULINE, PartOfSpeechType.NOUN) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.CONJUGATION_I, PartOfSpeechType.VERB) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.CONJUGATION_II, PartOfSpeechType.VERB) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.CONJUGATION_III, PartOfSpeechType.VERB) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.CONJUGATION_IV, PartOfSpeechType.VERB) },
                variationsGenerator.nextObject(AdjectiveVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.INFLECTIV_ADJECTIVE, PartOfSpeechType.ADJECTIVE) },
                variationsGenerator.nextObject(AdjectiveVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE, PartOfSpeechType.ADJECTIVE) },
                variationsGenerator.nextObject(NumeralVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.INFLECTIV_NUMERAL, PartOfSpeechType.NUMERAL) },
                variationsGenerator.nextObject(NumeralVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.UNINFLECTIV_NUMERAL, PartOfSpeechType.NUMERAL) },
                variationsGenerator.nextObject(NounPronounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.NOUN_PRONOUN, PartOfSpeechType.PRONOUN) },
                variationsGenerator.nextObject(AdjectivePronounVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.ADJECTIVE_PRONOUN, PartOfSpeechType.PRONOUN) },
                Triple(null, PartOfSpeechSubType.NUMERAL_PRONOUN, PartOfSpeechType.PRONOUN),
                Triple(null, PartOfSpeechSubType.ADVERB_PRONOUN, PartOfSpeechType.PRONOUN),
                variationsGenerator.nextObject(AdverbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Triple(it, PartOfSpeechSubType.ADVERB, PartOfSpeechType.ADVERB) },
                Triple(null, PartOfSpeechSubType.PREPOSITION, PartOfSpeechType.PREPOSITION),
                Triple(null, PartOfSpeechSubType.CONJUNCTION, PartOfSpeechType.CONJUNCTION),
                Triple(null, PartOfSpeechSubType.INTERJECTION, PartOfSpeechType.INTERJECTION),
                Triple(null, PartOfSpeechSubType.PARTICIPLE, PartOfSpeechType.PARTICIPLE),
        ).toTypedArray()

        exampleBySubtype = variations
            .groupBy { it.second.name }
            .mapValues { it.value.first().first }
    }

    fun variationsExamples(): Array<Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>> = variations
    fun exampleBySubtype(): Map<String, ObjectNode?> = exampleBySubtype
}
