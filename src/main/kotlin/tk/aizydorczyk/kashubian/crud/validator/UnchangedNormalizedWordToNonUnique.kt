package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.NORMALIZED_WORD_CHANGED_TO_NON_UNIQUE
import javax.servlet.http.HttpServletRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UnchangedNormalizedWordValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class UnchangedNormalizedWordToNonUnique(
    val message: String = NORMALIZED_WORD_CHANGED_TO_NON_UNIQUE,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class UnchangedNormalizedWordValidator : ConstraintValidator<UnchangedNormalizedWordToNonUnique, String?> {

    @Autowired
    private lateinit var repository: KashubianEntryRepository

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun isValid(word: String?, context: ConstraintValidatorContext?): Boolean {
        return word?.let(this::isChangedNormalizedWordUnique) ?: true
    }

    private fun isChangedNormalizedWordUnique(word: String): Boolean {
        val patchVariables =
            request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val entryId = patchVariables[ENTRY_ID]?.toLong() ?: 0
        return repository.countEntriesByNormalizedWordExcludeEntryId(entryId, word.normalize()) < 1
    }


}
