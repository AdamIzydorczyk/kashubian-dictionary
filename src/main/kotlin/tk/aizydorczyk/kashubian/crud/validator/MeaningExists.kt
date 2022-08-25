package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.MEANING_NOT_EXISTS
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [MeaningExistsValidator::class])
@Target(allowedTargets = [FIELD, VALUE_PARAMETER])
@Retention(RUNTIME)
annotation class MeaningExists(
    val message: String = MEANING_NOT_EXISTS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class MeaningExistsValidator : ConstraintValidator<MeaningExists, Long?> {

    @Autowired
    private lateinit var repository: KashubianEntryRepository

    override fun isValid(meaningId: Long?, context: ConstraintValidatorContext?): Boolean =
        meaningId?.let(repository::existsMeaningById) ?: true


}
