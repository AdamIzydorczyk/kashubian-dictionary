package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import javax.persistence.EntityManager

class MeaningUpdater(private val entityManager: EntityManager) {

    fun update(meaningId: Long, updatedMeaning: Meaning, entryIds: List<Long>): Meaning {
        updatedMeaning.id = meaningId
        entityManager.merge(updatedMeaning)
        updateEntryAssociations(meaningId, updatedMeaning, entryIds)
        return updatedMeaning
    }

    private fun updateEntryAssociations(meaningId: Long, meaning: Meaning, newEntryIds: List<Long>) {
        val currentEntries = entityManager.find(Meaning::class.java, meaningId).kashubianEntries
        val currentEntryIds = currentEntries.map { it.id }.toSet()
        val newEntryIdSet = newEntryIds.toSet()

        (currentEntryIds - newEntryIdSet).forEach { entryId ->
            entityManager.find(KashubianEntry::class.java, entryId)?.let { entry ->
                entry.meanings.removeIf { it.id == meaningId }
                entityManager.merge(entry)
            }
        }

        (newEntryIdSet - currentEntryIds).forEach { entryId ->
            entityManager.find(KashubianEntry::class.java, entryId)?.let { entry ->
                entry.meanings.add(meaning)
                entityManager.merge(entry)
            }
        }
    }
}
