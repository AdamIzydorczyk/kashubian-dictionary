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
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.ADJECTIVE_PRONOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.ADVERB
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.ADVERB_PRONOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.CONJUGATION_I
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.CONJUGATION_II
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.CONJUGATION_III
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.CONJUGATION_IV
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.CONJUNCTION
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.FEMININE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.INFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.INFLECTIV_NUMERAL
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.INTERJECTION
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.MASCULINE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.NEUTER
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.NON_MASCULINE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.NOUN_PRONOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.NUMERAL_PRONOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.PARTICIPLE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.PLURAL_MASCULINE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.PREPOSITION
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType.UNINFLECTIV_NUMERAL
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType.ADJECTIVE
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType.NOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType.NUMERAL
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType.PRONOUN
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType.VERB

@Component
class ExampleVariationsGenerator(val objectMapper: ObjectMapper) {
    private final val variations: Array<Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>>
    private final val exampleBySubtype: Map<String, ObjectNode?>
    private final val variationsGenerator = EasyRandom()

    init {
        variations = listOf(
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        NEUTER,
                        NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        MASCULINE,
                        NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        FEMININE,
                        NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        PLURAL_MASCULINE,
                        NOUN),
                prepareVariationJsonWithTypes(NounVariation::class.java,
                        NON_MASCULINE,
                        NOUN),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        CONJUGATION_I,
                        VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        CONJUGATION_II,
                        VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        CONJUGATION_III,
                        VERB),
                prepareVariationJsonWithTypes(VerbVariation::class.java,
                        CONJUGATION_IV,
                        VERB),
                prepareVariationJsonWithTypes(AdjectiveVariation::class.java,
                        INFLECTIV_ADJECTIVE,
                        ADJECTIVE),
                prepareVariationJsonWithTypes(AdjectiveVariation::class.java,
                        UNINFLECTIV_ADJECTIVE,
                        ADJECTIVE),
                prepareVariationJsonWithTypes(NumeralVariation::class.java,
                        INFLECTIV_NUMERAL,
                        NUMERAL),
                prepareVariationJsonWithTypes(NumeralVariation::class.java,
                        UNINFLECTIV_NUMERAL,
                        NUMERAL),
                prepareVariationJsonWithTypes(NounPronounVariation::class.java,
                        NOUN_PRONOUN,
                        PRONOUN),
                prepareVariationJsonWithTypes(AdjectivePronounVariation::class.java,
                        ADJECTIVE_PRONOUN,
                        PRONOUN),
                Triple(null, NUMERAL_PRONOUN, PRONOUN),
                Triple(null, ADVERB_PRONOUN, PRONOUN),
                prepareVariationJsonWithTypes(AdverbVariation::class.java,
                        ADVERB,
                        PartOfSpeechType.ADVERB),
                Triple(null, PREPOSITION, PartOfSpeechType.PREPOSITION),
                Triple(null, CONJUNCTION, PartOfSpeechType.CONJUNCTION),
                Triple(null, INTERJECTION, PartOfSpeechType.INTERJECTION),
                Triple(null, PARTICIPLE, PartOfSpeechType.PARTICIPLE),
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