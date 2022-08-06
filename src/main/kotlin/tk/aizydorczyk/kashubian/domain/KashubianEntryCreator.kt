package tk.aizydorczyk.kashubian.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(entityManager: EntityManager) : KashubianEntryApplyer(entityManager) {
    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry =
        persistAllEntryContentAndAssignRelations(entry).apply(entityManager::persist)
}