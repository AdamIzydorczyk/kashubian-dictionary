package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Antonym
import javax.persistence.EntityManager

class AntonymUpdater(private val entityManager: EntityManager) {

    fun update(antonymId: Long, updated: Antonym): Antonym {
        val old = entityManager.find(Antonym::class.java, antonymId)
        updated.id = antonymId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
