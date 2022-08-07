package tk.aizydorczyk.kashubian.domain.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping
import javax.persistence.EntityManager
import javax.servlet.http.HttpServletRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UnchangedWordValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class UnchangedToNonUnique(
    val message: String = "WORD_CHANGED_TO_NON_UNIQUE",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class UnchangedWordValidator : ConstraintValidator<UnchangedToNonUnique, String> {

    @Autowired
    @Qualifier("defaultEntityManager")
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun isValid(word: String, context: ConstraintValidatorContext?): Boolean {
        return word.let(this::isChangedWordUnique)
    }

    private fun isChangedWordUnique(word: String): Boolean {
        val patchVariables =
            request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val entryId = patchVariables["entryId"]?.toLong() ?: 0
        return entityManager.createQuery("select count(e) from KashubianEntry e where e.word = :word and e.id != :id",
                Long::class.javaObjectType)
            .setParameter("word", word)
            .setParameter("id", entryId)
            .singleResult < 1
    }


}
