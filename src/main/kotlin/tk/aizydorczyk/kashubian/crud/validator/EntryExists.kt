package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.ENTRY_NOT_EXISTS
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [EntryExistsIdValidator::class])
@Target(allowedTargets = [FIELD, VALUE_PARAMETER])
@Retention(RUNTIME)
annotation class EntryExists(
    val message: String = ENTRY_NOT_EXISTS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class EntryExistsIdValidator : ConstraintValidator<EntryExists, Long?> {

    @Autowired
    private lateinit var repository: KashubianEntryRepository

    override fun isValid(entryId: Long?, context: ConstraintValidatorContext?): Boolean {
        return entryId?.let(this::isEntryExist) ?: true
    }

    private fun isEntryExist(entryId: Long): Boolean {
        return repository.countEntriesById(entryId) > 0
    }

}
