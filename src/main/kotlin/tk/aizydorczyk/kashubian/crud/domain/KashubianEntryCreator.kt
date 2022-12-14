package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.ChildEntity
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import javax.persistence.EntityManager

class KashubianEntryCreator(val entityManager: EntityManager) {

    fun create(entry: KashubianEntry,
        auditingInformation: AuditingInformation): KashubianEntry {
        entry.createdAt = auditingInformation.executionTime
        entry.createdBy = auditingInformation.userName

        entry.normalizedWord = entry.word?.normalize()
        entityManager.persist(entry)

        entry.others.assignParentToAllAndPersist(entry.id, entityManager)

        entry.meanings.forEach { meaning ->
            with(meaning) {
                apply { kashubianEntry = entry.id }

                entityManager.persist(this)

                proverbs.assignParentToAllAndPersist(this.id, entityManager)
                idioms.assignParentToAllAndPersist(this.id, entityManager)
                quotes.assignParentToAllAndPersist(this.id, entityManager)
                examples.assignParentToAllAndPersist(this.id, entityManager)
                antonyms.assignParentToAllAndPersist(this.id, entityManager)
                synonyms.assignParentToAllAndPersist(this.id, entityManager)

                translation?.let {
                    entityManager.persist(it.copyWitchNormalizedFieldsAndAssignedId(this.id))
                }
            }
        }
        return entry
    }

}

fun List<ChildEntity>.assignParentToAllAndPersist(parentId: Long, entityManager: EntityManager) {
    this.onEach { it.setParentId(parentId) }.forEach(entityManager::persist)
}
