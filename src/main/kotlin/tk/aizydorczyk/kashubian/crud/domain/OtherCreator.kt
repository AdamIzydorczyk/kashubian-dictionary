package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Other
import javax.persistence.EntityManager

class OtherCreator(private val entityManager: EntityManager) {

    fun create(entryId: Long, other: Other): Other {
        other.kashubianEntry = entryId
        entityManager.persist(other)
        return other
    }
}
