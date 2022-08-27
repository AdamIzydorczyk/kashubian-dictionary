package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.HYPERONIM_IDS_REPEATED_IN_MEANINGS
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [HyperonimsCannotRepeatedInMeaningsValidator::class])
@Target(allowedTargets = [FIELD])
@Retention(RUNTIME)
annotation class HyperonimIdsCannotRepeatedInMeanings(
    val message: String = HYPERONIM_IDS_REPEATED_IN_MEANINGS,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class HyperonimsCannotRepeatedInMeaningsValidator :
    ConstraintValidator<HyperonimIdsCannotRepeatedInMeanings, List<MeaningDto>> {

    override fun isValid(dtos: List<MeaningDto>, context: ConstraintValidatorContext?): Boolean {
        val newHyperonimIds = dtos.mapNotNull { it.hyperonym }
        val uniqueNewHyperonimIds = newHyperonimIds.distinct()

        return newHyperonimIds.size == uniqueNewHyperonimIds.size
    }
}
