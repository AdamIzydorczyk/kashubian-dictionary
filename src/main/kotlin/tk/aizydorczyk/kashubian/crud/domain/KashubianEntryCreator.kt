package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.extension.assignParentToAllAndPersist
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Variation
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.DEFAULT_ENTITY_MANAGER
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(@Qualifier(DEFAULT_ENTITY_MANAGER) val entityManager: EntityManager) {
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
                phrasalVerbs.assignParentToAllAndPersist(this.id, entityManager)
                quotes.assignParentToAllAndPersist(this.id, entityManager)
                examples.assignParentToAllAndPersist(this.id, entityManager)
                antonyms.assignParentToAllAndPersist(this.id, entityManager)
                synonyms.assignParentToAllAndPersist(this.id, entityManager)

                translation?.let {
                    entityManager.persist(it.copyWitchNormalizedFieldsAndAssignedId(this.id))
                }
            }
        }
        return entry.apply {
            variation?.let {
                entityManager.persist(Variation(id, it.variation, id))
            }
        }
    }

}
