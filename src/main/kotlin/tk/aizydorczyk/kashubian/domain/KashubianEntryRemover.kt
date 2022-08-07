package tk.aizydorczyk.kashubian.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import javax.persistence.EntityManager

@Component
class KashubianEntryRemover(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {

    @Transactional
    fun remove(entryId: Long) {
        val entry = entityManager.find(KashubianEntry::class.java, entryId)
        entry.meanings.forEach {
            it.proverbs.forEach(entityManager::remove)
            it.examples.forEach(entityManager::remove)
            it.quotes.forEach(entityManager::remove)
            it.phrasalVerbs.forEach(entityManager::remove)
            entityManager.remove(it)
        }
        entityManager.remove(entry)
    }
}