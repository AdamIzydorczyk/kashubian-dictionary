package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Antonym
import javax.persistence.EntityManager

class AntonymCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, antonym: Antonym): Antonym {
        antonym.meaning = meaningId
        entityManager.persist(antonym)
        return antonym
    }
}
