package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS
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
annotation class HyperonimIdNotInUpdatedEntryMeaningsHyperonims(
    val message: String = HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class NotInUpdatedEntryMeaningsHyperonimsValidator :
    ConstraintValidator<HyperonimIdNotInUpdatedEntryMeaningsHyperonims, List<MeaningDto>> {

    @Autowired
    private lateinit var request: HttpServletRequest

    @Autowired
    private lateinit var kashubianEntryRepository: KashubianEntryRepository

    @Transactional(readOnly = true)
    override fun isValid(dtos: List<MeaningDto>, context: ConstraintValidatorContext?): Boolean {
        return dtos.mapNotNull { it.hyperonym }
            .let(this::isNotInUpdatedEntryMeaningsHyperonims)
    }

    @Suppress("UNCHECKED_CAST")
    private fun isNotInUpdatedEntryMeaningsHyperonims(newHyperonimIds: List<Long>): Boolean {
        val patchVariables =
            request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE) as LinkedHashMap<String, String>
        val updatedEntryId = patchVariables[ENTRY_ID]?.toLong() ?: 0

        val baseFieldAndRestOfFieldsPairs = kashubianEntryRepository.findMeaningIdsByEntryId(updatedEntryId)
            .map {
                val hyperonymIds = kashubianEntryRepository.findHyperonyms(it).map { element -> element.meaningId }

                val currentBaseField = hyperonymIds.firstOrNull()

                val restOfBases = if (hyperonymIds.isNotEmpty()) {
                    hyperonymIds.subList(1, hyperonymIds.size)
                } else {
                    emptyList()
                }

                Pair(currentBaseField, restOfBases)
            }

        val currentHyperonimFields = baseFieldAndRestOfFieldsPairs.map { it.first }
        val restOfHyperonims = baseFieldAndRestOfFieldsPairs.map { it.second }.flatten()


        return currentHyperonimFields.all { it == null || it in newHyperonimIds } || newHyperonimIds.all { it !in restOfHyperonims }
    }

}
