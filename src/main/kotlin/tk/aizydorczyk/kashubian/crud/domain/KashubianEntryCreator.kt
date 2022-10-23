package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.ChildEntity
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(val entityManager: EntityManager) {
    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry {
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
