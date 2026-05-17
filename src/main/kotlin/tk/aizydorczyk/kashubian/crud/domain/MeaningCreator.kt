package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import javax.persistence.EntityManager

class MeaningCreator(private val entityManager: EntityManager) {

    fun create(meaning: Meaning, entryIds: List<Long>): Meaning {
        entityManager.persist(meaning)

        entryIds.forEach { entryId ->
            entityManager.find(KashubianEntry::class.java, entryId)?.let { entry ->
                entry.meanings.add(meaning)
                entityManager.merge(entry)
            }
        }

        return meaning
    }
}
