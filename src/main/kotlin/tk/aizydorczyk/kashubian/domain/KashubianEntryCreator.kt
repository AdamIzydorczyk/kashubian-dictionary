package tk.aizydorczyk.kashubian.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Meaning
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(val entityManager: EntityManager) {

    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry {
        return save(entry)
    }

    private fun save(entry: KashubianEntry): KashubianEntry {
        entry.variation.let(entityManager::persist)

        entry.meanings.forEach {
            it.translation.let(entityManager::persist)
            it.proverbs.forEach(entityManager::persist)
            it.phrasalVerbs.forEach(entityManager::persist)
            it.quotes.forEach(entityManager::persist)
            it.examples.forEach(entityManager::persist)

            it.base = it.base?.let { base ->
                entityManager.find(Meaning::class.java, base.id)
            }

            it.superordinate = it.superordinate?.let { superordinate ->
                entityManager.find(Meaning::class.java, superordinate.id)
            }

            entityManager.persist(it)
        }
        entityManager.persist(entry)
        return entry
    }
}