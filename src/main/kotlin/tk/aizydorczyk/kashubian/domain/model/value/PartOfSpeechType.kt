package tk.aizydorczyk.kashubian.domain.model.value

import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.ADJECTIVE_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.ADVERB_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_I
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_II
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_III
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_IV
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.FEMININE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.INFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.INFLECTIV_NUMERAL
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NEUTER
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NON_MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NOUN_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NUMERAL_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.PLURAL_MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.UNINFLECTIV_NUMERAL

enum class PartOfSpeechType(val subTypes: List<PartOfSpeechSubType>) {
    NOUN(listOf(NEUTER,
            MASCULINE,
            FEMININE,
            PLURAL_MASCULINE,
            NON_MASCULINE)),
    VERB(listOf(CONJUGATION_I,
            CONJUGATION_II,
            CONJUGATION_III,
            CONJUGATION_IV)),
    ADJECTIVE(listOf(INFLECTIV_ADJECTIVE,
            UNINFLECTIV_ADJECTIVE)),
    NUMERAL(listOf(INFLECTIV_NUMERAL,
            UNINFLECTIV_NUMERAL)),
    PRONOUN(listOf(NOUN_PRONOUN,
            ADJECTIVE_PRONOUN,
            NUMERAL_PRONOUN,
            ADVERB_PRONOUN)),
    ADVERB(listOf(PartOfSpeechSubType.ADVERB)),
    PREPOSITION(listOf(PartOfSpeechSubType.PREPOSITION)),
    CONJUNCTION(listOf(PartOfSpeechSubType.CONJUNCTION)),
    INTERJECTION(listOf(PartOfSpeechSubType.INTERJECTION)),
    PARTICIPLE(listOf(PartOfSpeechSubType.PARTICIPLE))
}

enum class PartOfSpeechSubType {
    NEUTER,
    MASCULINE,
    FEMININE,
    PLURAL_MASCULINE,
    NON_MASCULINE,
    CONJUGATION_I,
    CONJUGATION_II,
    CONJUGATION_III,
    CONJUGATION_IV,
    INFLECTIV_ADJECTIVE,
    UNINFLECTIV_ADJECTIVE,
    INFLECTIV_NUMERAL,
    UNINFLECTIV_NUMERAL,
    NOUN_PRONOUN,
    ADJECTIVE_PRONOUN,
    NUMERAL_PRONOUN,
    ADVERB_PRONOUN,
    ADVERB,
    PREPOSITION,
    CONJUNCTION,
    INTERJECTION,
    PARTICIPLE
}