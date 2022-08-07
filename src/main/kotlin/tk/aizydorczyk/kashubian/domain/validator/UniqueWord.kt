package tk.aizydorczyk.kashubian.domain.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [UniqueWordValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class UniqueWord(
    val message: String = "WORD_NOT_UNIQUE",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class UniqueWordValidator : ConstraintValidator<UniqueWord, String> {

    @Autowired
    @Qualifier("defaultEntityManager")
    private lateinit var entityManager: EntityManager

    override fun isValid(word: String, context: ConstraintValidatorContext?): Boolean {
        return word.let(this::isWordNonExistOrEqual)
    }

    private fun isWordNonExistOrEqual(word: String): Boolean {
        return entityManager.createQuery("select count(e) from KashubianEntry e where e.word = :word",
                Long::class.javaObjectType)
            .setParameter("word", word).singleResult < 1
    }
}
