package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.extension.normalize
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UniqueNormalizedWordValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class UniqueNormalizedWord(
    val message: String = "NORMALIZED_WORD_NOT_UNIQUE",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class UniqueNormalizedWordValidator : ConstraintValidator<UniqueNormalizedWord, String?> {

    @Autowired
    private lateinit var repository: KashubianEntryRepository

    override fun isValid(word: String?, context: ConstraintValidatorContext?): Boolean {
        return word?.let(this::isNormalizedWordNonExistOrEqual) ?: true
    }

    private fun isNormalizedWordNonExistOrEqual(word: String): Boolean {
        return repository.countEntriesByNormalizedWord(word.normalize()) < 1
    }
}
