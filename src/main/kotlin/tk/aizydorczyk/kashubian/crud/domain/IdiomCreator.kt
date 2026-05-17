package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Idiom
import javax.persistence.EntityManager

class IdiomCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, idiom: Idiom): Idiom {
        idiom.meaning = meaningId
        entityManager.persist(idiom)
        return idiom
    }
}
