package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import javax.persistence.EntityManager

class TranslationUpdater(private val entityManager: EntityManager) {

    fun update(meaningId: Long, updated: Translation): Translation {
        val withId = updated.copyWithNormalizedFieldsAndAssignedId(meaningId)
        entityManager.merge(withId)
        return withId
    }
}
