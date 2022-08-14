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
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType.ADJECTIVE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType.NOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType.NUMERAL
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType.PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType.VERB

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

enum class PartOfSpeechSubType(val partOfSpeechType: PartOfSpeechType) {
    NEUTER(NOUN),
    MASCULINE(NOUN),
    FEMININE(NOUN),
    PLURAL_MASCULINE(NOUN),
    NON_MASCULINE(NOUN),
    CONJUGATION_I(VERB),
    CONJUGATION_II(VERB),
    CONJUGATION_III(VERB),
    CONJUGATION_IV(VERB),
    INFLECTIV_ADJECTIVE(ADJECTIVE),
    UNINFLECTIV_ADJECTIVE(ADJECTIVE),
    INFLECTIV_NUMERAL(NUMERAL),
    UNINFLECTIV_NUMERAL(NUMERAL),
    NOUN_PRONOUN(PRONOUN),
    ADJECTIVE_PRONOUN(PRONOUN),
    NUMERAL_PRONOUN(PRONOUN),
    ADVERB_PRONOUN(PRONOUN),
    ADVERB(PartOfSpeechType.ADVERB),
    PREPOSITION(PartOfSpeechType.PREPOSITION),
    CONJUNCTION(PartOfSpeechType.CONJUNCTION),
    INTERJECTION(PartOfSpeechType.INTERJECTION),
    PARTICIPLE(PartOfSpeechType.PARTICIPLE)
}