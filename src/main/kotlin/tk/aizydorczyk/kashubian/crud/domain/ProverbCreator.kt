package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Proverb
import javax.persistence.EntityManager

class ProverbCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, proverb: Proverb): Proverb {
        proverb.meaning = meaningId
        entityManager.persist(proverb)
        return proverb
    }
}
