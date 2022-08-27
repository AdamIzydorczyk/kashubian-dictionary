package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.MEANING_OF_UPDATED_ENTRY
import javax.servlet.http.HttpServletRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [NotMeaningOfUpdatedEntryValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class NotMeaningOfUpdatedEntry(
    val message: String = MEANING_OF_UPDATED_ENTRY,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class NotMeaningOfUpdatedEntryValidator : ConstraintValidator<NotMeaningOfUpdatedEntry, Long?> {

    @Autowired
    private lateinit var repository: KashubianEntryRepository

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun isValid(meaningId: Long?, context: ConstraintValidatorContext?): Boolean {
        return meaningId?.let(this::isMeaningOfUpdatedEntry) ?: true
    }

    @Suppress("UNCHECKED_CAST")
    private fun isMeaningOfUpdatedEntry(meaningId: Long): Boolean {
        val patchVariables =
            request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val entryId = patchVariables[ENTRY_ID]?.toLong() ?: 0
        return repository.notExistsMeaningByEntryIdAndMeaningId(entryId, meaningId)
    }

}
