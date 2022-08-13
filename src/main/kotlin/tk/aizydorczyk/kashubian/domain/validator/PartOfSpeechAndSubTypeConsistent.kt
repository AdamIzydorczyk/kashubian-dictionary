package tk.aizydorczyk.kashubian.domain.validator

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [PartOfSpeechAndSubTypeConsistentValidator::class])
@Target(allowedTargets = [CLASS])
@Retention(RUNTIME)
annotation class PartOfSpeechAndSubTypeConsistent(
    val message: String = "PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class PartOfSpeechAndSubTypeConsistentValidator :
    ConstraintValidator<PartOfSpeechAndSubTypeConsistent, KashubianEntryDto> {

    override fun isValid(dto: KashubianEntryDto, context: ConstraintValidatorContext?): Boolean {
        if (dto.partOfSpeech == null) return true
        return dto.partOfSpeechSubType in dto.partOfSpeech.subTypes
    }
}
