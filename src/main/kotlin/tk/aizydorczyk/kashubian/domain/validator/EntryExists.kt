package tk.aizydorczyk.kashubian.domain.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import javax.persistence.EntityManager
import javax.servlet.http.HttpServletRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [EntryExistsDtoValidator::class, EntryExistsIdValidator::class])
@Target(allowedTargets = [CLASS, FIELD, VALUE_PARAMETER])
@Retention(RUNTIME)
annotation class EntryExists(
    val message: String = "ENTRY_NOT_EXISTS",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class EntryExistsDtoValidator : ConstraintValidator<EntryExists, KashubianEntryDto> {

    @Autowired
    @Qualifier("defaultEntityManager")
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun isValid(dto: KashubianEntryDto, context: ConstraintValidatorContext?): Boolean {
        val patchVariables =
            request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val entryId = patchVariables["entryId"]?.toLong() ?: 0
        return entityManager.createQuery("select count(e) from KashubianEntry e where e.id = :id",
                Long::class.javaObjectType)
            .setParameter("id", entryId)
            .singleResult > 0
    }

}

@Component
@RequestScope
class EntryExistsIdValidator : ConstraintValidator<EntryExists, Long?> {

    @Autowired
    @Qualifier("defaultEntityManager")
    private lateinit var entityManager: EntityManager

    override fun isValid(entryId: Long?, context: ConstraintValidatorContext?): Boolean {
        return entryId?.let(this::isEntryExist) ?: true
    }

    private fun isEntryExist(entryId: Long): Boolean {
        return entityManager.createQuery("select count(e) from KashubianEntry e where e.id = :id",
                Long::class.javaObjectType)
            .setParameter("id", entryId)
            .singleResult > 0
    }

}
