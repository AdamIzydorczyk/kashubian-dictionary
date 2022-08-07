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
@Constraint(validatedBy = [MeaningExistsValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class MeaningExists(
    val message: String = "MEANING_NOT_EXISTS",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class MeaningExistsValidator : ConstraintValidator<MeaningExists, Long?> {

    @Autowired
    @Qualifier("defaultEntityManager")
    private lateinit var entityManager: EntityManager

    override fun isValid(meaningId: Long?, context: ConstraintValidatorContext?): Boolean {
        return meaningId?.let(this::isMeaningExist) ?: true
    }

    private fun isMeaningExist(meaningId: Long): Boolean {
        return entityManager.createQuery("select count(m) from Meaning m where m.id = :id",
                Long::class.javaObjectType)
            .setParameter("id", meaningId).singleResult > 0
    }
}
