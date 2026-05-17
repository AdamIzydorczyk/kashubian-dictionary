package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import javax.persistence.EntityManager

class KashubianEntryCreator(val entityManager: EntityManager) {

    fun create(entry: KashubianEntry,
        auditingInformation: AuditingInformation): KashubianEntry {
        entry.createdAt = auditingInformation.executionTime
        entry.createdBy = auditingInformation.userName

        entry.normalizedWord = entry.word?.normalize()
        entry.meanings.clear()
        entityManager.persist(entry)
        return entry
    }

}
