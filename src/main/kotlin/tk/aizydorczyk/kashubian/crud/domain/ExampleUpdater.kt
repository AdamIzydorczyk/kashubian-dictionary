package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Example
import javax.persistence.EntityManager

class ExampleUpdater(private val entityManager: EntityManager) {

    fun update(exampleId: Long, updated: Example): Example {
        val old = entityManager.find(Example::class.java, exampleId)
        updated.id = exampleId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
