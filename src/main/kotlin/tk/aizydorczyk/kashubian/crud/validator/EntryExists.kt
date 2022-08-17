package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
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
    private lateinit var repository: KashubianEntryRepository

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun isValid(dto: KashubianEntryDto, context: ConstraintValidatorContext?): Boolean {
        val patchVariables =
            request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val entryId = patchVariables["entryId"]?.toLong() ?: 0
        return repository.countEntriesById(entryId) > 0
    }

}

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
