package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Other
import javax.persistence.EntityManager

class OtherUpdater(private val entityManager: EntityManager) {

    fun update(otherId: Long, updated: Other): Other {
        val old = entityManager.find(Other::class.java, otherId)
        updated.id = otherId
        updated.kashubianEntry = old.kashubianEntry
        entityManager.merge(updated)
        return updated
    }
}
