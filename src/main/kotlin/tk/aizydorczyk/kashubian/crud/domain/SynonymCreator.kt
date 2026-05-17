package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Synonym
import javax.persistence.EntityManager

class SynonymCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, synonym: Synonym): Synonym {
        synonym.meaning = meaningId
        entityManager.persist(synonym)
        return synonym
    }
}
