package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import javax.persistence.EntityManager

class KashubianEntryUpdater(private val entityManager: EntityManager) {

    fun update(entryId: Long, updatedEntry: KashubianEntry,
        auditingInformation: AuditingInformation): KashubianEntry {
        updatedEntry.normalizedWord = updatedEntry.word?.normalize()

        updatedEntry.modifiedAt = auditingInformation.executionTime
        updatedEntry.modifiedBy = auditingInformation.userName

        val oldEntry = entityManager.find(KashubianEntry::class.java, entryId)
        updatedEntry.createdBy = oldEntry.createdBy
        updatedEntry.createdAt = oldEntry.createdAt

        updatedEntry.id = entryId
        updatedEntry.meanings.clear()
        updatedEntry.meanings.addAll(oldEntry.meanings)

        entityManager.merge(updatedEntry)
        return updatedEntry
    }
}
