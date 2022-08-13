package tk.aizydorczyk.kashubian.domain.validator

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.dto.VariationDto
import tk.aizydorczyk.kashubian.domain.model.json.AdjectivePronounVariation
import tk.aizydorczyk.kashubian.domain.model.json.AdjectiveVariation
import tk.aizydorczyk.kashubian.domain.model.json.AdverbVariation
import tk.aizydorczyk.kashubian.domain.model.json.NounPronounVariation
import tk.aizydorczyk.kashubian.domain.model.json.NounVariation
import tk.aizydorczyk.kashubian.domain.model.json.NumeralVariation
import tk.aizydorczyk.kashubian.domain.model.json.VerbVariation
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.ADJECTIVE_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.ADVERB
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.ADVERB_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_I
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_II
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_III
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUGATION_IV
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.CONJUNCTION
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.FEMININE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.INFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.INFLECTIV_NUMERAL
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.INTERJECTION
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NEUTER
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NON_MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NOUN_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.NUMERAL_PRONOUN
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.PARTICIPLE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.PLURAL_MASCULINE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.PREPOSITION
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType.UNINFLECTIV_NUMERAL
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [CorrectVariationJsonFormatByPartOfSpeechSubTypeValidator::class])
@Target(allowedTargets = [CLASS])
@Retention(RUNTIME)
annotation class CorrectVariationJsonFormatByPartOfSpeechSubType(
    val message: String = "INCORRECT_JSON_FORMAT",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class CorrectVariationJsonFormatByPartOfSpeechSubTypeValidator :
    ConstraintValidator<CorrectVariationJsonFormatByPartOfSpeechSubType, KashubianEntryDto> {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun isValid(dto: KashubianEntryDto, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        return dto.partOfSpeechSubType?.let {
            return when (it) {
                MASCULINE,
                FEMININE,
                NEUTER,
                PLURAL_MASCULINE,
                NON_MASCULINE -> validateVariation(
                        context,
                        dto.variation,
                        NounVariation::class.java,
                        "INCORRECT_NOUN_VARIATION_JSON_FORMAT")

                CONJUGATION_I,
                CONJUGATION_II,
                CONJUGATION_III,
                CONJUGATION_IV -> validateVariation(
                        context,
                        dto.variation,
                        VerbVariation::class.java,
                        "INCORRECT_VERB_VARIATION_JSON_FORMAT")

                INFLECTIV_ADJECTIVE,
                UNINFLECTIV_ADJECTIVE -> validateVariation(
                        context,
                        dto.variation,
                        AdjectiveVariation::class.java,
                        "INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT")

                INFLECTIV_NUMERAL,
                UNINFLECTIV_NUMERAL -> validateVariation(
                        context,
                        dto.variation,
                        NumeralVariation::class.java,
                        "INCORRECT_NUMERAL_VARIATION_JSON_FORMAT")

                NOUN_PRONOUN -> validateVariation(
                        context,
                        dto.variation,
                        NounPronounVariation::class.java,
                        "INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT")

                ADJECTIVE_PRONOUN -> validateVariation(
                        context,
                        dto.variation,
                        AdjectivePronounVariation::class.java,
                        "INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT")

                NUMERAL_PRONOUN,
                ADVERB_PRONOUN,
                ADVERB -> validateVariation(
                        context,
                        dto.variation,
                        AdverbVariation::class.java,
                        "INCORRECT_ADVERB_VARIATION_JSON_FORMAT")

                PREPOSITION,
                CONJUNCTION,
                INTERJECTION,
                PARTICIPLE -> dto.variation?.let {
                    context.buildConstraintViolationWithTemplate("VARIATION_IS_NOT_NULL")
                        .addConstraintViolation()
                    false
                } ?: true
            }
        } ?: true
    }


    private fun <T> validateVariation(context: ConstraintValidatorContext,
        variation: VariationDto?,
        jsonModelType: Class<T>,
        message: String): Boolean = variation?.let { dto ->
        dto.variation?.let {
            try {
                objectMapper.treeToValue(it, jsonModelType)
                true
            } catch (ex: JsonMappingException) {
                context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                false
            }
        }
    } ?: run {
        context.buildConstraintViolationWithTemplate("VARIATION_IS_NULL")
            .addConstraintViolation()
        false
    }

}
