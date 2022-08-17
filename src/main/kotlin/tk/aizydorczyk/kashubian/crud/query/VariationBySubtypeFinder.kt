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
    private final val variationsGenerator = EasyRandom()
    init {
        variations = listOf(
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PartOfSpeechSubType.NEUTER,
                        PartOfSpeechType.NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PartOfSpeechSubType.MASCULINE,
                        PartOfSpeechType.NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PartOfSpeechSubType.FEMININE,
                        PartOfSpeechType.NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PartOfSpeechSubType.PLURAL_MASCULINE,
                        PartOfSpeechType.NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PartOfSpeechSubType.NON_MASCULINE,
                        PartOfSpeechType.NOUN),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        PartOfSpeechSubType.CONJUGATION_I,
                        PartOfSpeechType.VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        PartOfSpeechSubType.CONJUGATION_II,
                        PartOfSpeechType.VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        PartOfSpeechSubType.CONJUGATION_III,
                        PartOfSpeechType.VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        PartOfSpeechSubType.CONJUGATION_IV,
                        PartOfSpeechType.VERB),
                prepareVariationJsonWithTypes(AdjectiveVariation::class.java,
                        PartOfSpeechSubType.INFLECTIV_ADJECTIVE,
                        PartOfSpeechType.ADJECTIVE),
                prepareVariationJsonWithTypes(AdjectiveVariation::class.java,
                        PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE,
                        PartOfSpeechType.ADJECTIVE),
                prepareVariationJsonWithTypes(NumeralVariation::class.java,
                        PartOfSpeechSubType.INFLECTIV_NUMERAL,
                        PartOfSpeechType.NUMERAL),
                prepareVariationJsonWithTypes(NumeralVariation::class.java,
                        PartOfSpeechSubType.UNINFLECTIV_NUMERAL,
                        PartOfSpeechType.NUMERAL),
                prepareVariationJsonWithTypes(NounPronounVariation::class.java,
                        PartOfSpeechSubType.NOUN_PRONOUN,
                        PartOfSpeechType.PRONOUN),
                prepareVariationJsonWithTypes(AdjectivePronounVariation::class.java,
                        PartOfSpeechSubType.ADJECTIVE_PRONOUN,
                        PartOfSpeechType.PRONOUN),
                Triple(null, PartOfSpeechSubType.NUMERAL_PRONOUN, PartOfSpeechType.PRONOUN),
                Triple(null, PartOfSpeechSubType.ADVERB_PRONOUN, PartOfSpeechType.PRONOUN),
                prepareVariationJsonWithTypes(AdverbVariation::class.java,
                        PartOfSpeechSubType.ADVERB,
                        PartOfSpeechType.ADVERB),
                Triple(null, PartOfSpeechSubType.PREPOSITION, PartOfSpeechType.PREPOSITION),
                Triple(null, PartOfSpeechSubType.CONJUNCTION, PartOfSpeechType.CONJUNCTION),
                Triple(null, PartOfSpeechSubType.INTERJECTION, PartOfSpeechType.INTERJECTION),
                Triple(null, PartOfSpeechSubType.PARTICIPLE, PartOfSpeechType.PARTICIPLE),
        ).toTypedArray()

        exampleBySubtype = variations
            .groupBy { it.second.name }
            .mapValues { it.value.first().first }
    }

    private final fun prepareVariationJsonWithTypes(variationClass: Class<*>,
        partOfSpeechSubType: PartOfSpeechSubType,
        partOfSpeechType: PartOfSpeechType) = variationsGenerator.nextObject(variationClass)
        .let { objectMapper.convertValue(it, ObjectNode::class.java) }
        .let { Triple(it, partOfSpeechSubType, partOfSpeechType) }

    fun variationsExamples(): Array<Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>> = variations
    fun exampleBySubtype(): Map<String, ObjectNode?> = exampleBySubtype
}
