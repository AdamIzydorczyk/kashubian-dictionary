package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Idiom
import javax.persistence.EntityManager

class IdiomUpdater(private val entityManager: EntityManager) {

    fun update(idiomId: Long, updated: Idiom): Idiom {
        val old = entityManager.find(Idiom::class.java, idiomId)
        updated.id = idiomId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
