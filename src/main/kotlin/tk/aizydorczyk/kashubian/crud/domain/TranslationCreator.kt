package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import javax.persistence.EntityManager

class TranslationCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, translation: Translation): Translation {
        val withId = translation.copyWithNormalizedFieldsAndAssignedId(meaningId)
        entityManager.merge(withId)
        return withId
    }
}
