package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS
import javax.servlet.http.HttpServletRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [NotInUpdatedEntryMeaningsHyperonimsValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class NotInUpdatedEntryMeaningsHyperonims(
    val message: String = IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class NotInUpdatedEntryMeaningsHyperonimsValidator : ConstraintValidator<NotInUpdatedEntryMeaningsHyperonims, Long?> {

    @Autowired
    private lateinit var request: HttpServletRequest

    @Autowired
    private lateinit var kashubianEntryRepository: KashubianEntryRepository

    @Transactional(readOnly = true)
    override fun isValid(meaningId: Long?, context: ConstraintValidatorContext?): Boolean {
        return meaningId?.let(this::isNotInUpdatedEntryMeaningsHyperonims) ?: true
    }

    @Suppress("UNCHECKED_CAST")
    private fun isNotInUpdatedEntryMeaningsHyperonims(meaningId: Long): Boolean {
        val patchVariables =
            request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val updatedEntryId = patchVariables[ENTRY_ID]?.toLong() ?: 0

        val hyperonymIds = kashubianEntryRepository.findMeaningIdsByEntryId(updatedEntryId)
            .map { kashubianEntryRepository.findHyperonyms(it) }
            .flatten().map { it.meaningId }

        return meaningId !in hyperonymIds
    }

}
