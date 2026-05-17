package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Example
import javax.persistence.EntityManager

class ExampleCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, example: Example): Example {
        example.meaning = meaningId
        entityManager.persist(example)
        return example
    }
}
