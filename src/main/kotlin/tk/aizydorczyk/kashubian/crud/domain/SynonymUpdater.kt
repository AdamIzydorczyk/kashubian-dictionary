package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Synonym
import javax.persistence.EntityManager

class SynonymUpdater(private val entityManager: EntityManager) {

    fun update(synonymId: Long, updated: Synonym): Synonym {
        val old = entityManager.find(Synonym::class.java, synonymId)
        updated.id = synonymId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
