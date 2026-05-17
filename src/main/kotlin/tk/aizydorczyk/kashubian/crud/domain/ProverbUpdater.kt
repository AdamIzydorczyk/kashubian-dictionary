package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Proverb
import javax.persistence.EntityManager

class ProverbUpdater(private val entityManager: EntityManager) {

    fun update(proverbId: Long, updated: Proverb): Proverb {
        val old = entityManager.find(Proverb::class.java, proverbId)
        updated.id = proverbId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
