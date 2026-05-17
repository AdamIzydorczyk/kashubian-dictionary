package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.crud.domain.MeaningRepository
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.QUOTE_NOT_EXISTS
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [QuoteExistsValidator::class])
@Target(allowedTargets = [VALUE_PARAMETER])
@Retention(RUNTIME)
annotation class QuoteExists(
    val message: String = QUOTE_NOT_EXISTS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class QuoteExistsValidator : ConstraintValidator<QuoteExists, Long?> {

    @Autowired
    private lateinit var repository: MeaningRepository

    override fun isValid(id: Long?, context: ConstraintValidatorContext?): Boolean =
        id?.let(repository::existsQuoteById) ?: true
}
