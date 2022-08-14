package tk.aizydorczyk.kashubian.domain

import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Meaning
import javax.persistence.EntityManager

abstract class KashubianEntryApplyer(val entityManager: EntityManager) {
    protected fun persistAllEntryContentAndAssignRelations(entry: KashubianEntry): KashubianEntry {
        entry.variation?.let(entityManager::persist)
        entry.others.forEach {
            it.other = entityManager.find(KashubianEntry::class.java, it.other.id)
            entityManager.persist(it)
        }

        entry.meanings.forEach { meaning ->
            meaning.translation.let(entityManager::persist)
            meaning.proverbs.forEach(entityManager::persist)
            meaning.phrasalVerbs.forEach(entityManager::persist)
            meaning.quotes.forEach(entityManager::persist)
            meaning.examples.forEach(entityManager::persist)

            meaning.base = meaning.base?.let { base ->
                entityManager.find(Meaning::class.java, base.id)
            }

            meaning.superordinate = meaning.superordinate?.let { superordinate ->
                entityManager.find(Meaning::class.java, superordinate.id)
            }

            meaning.antonyms.forEach { antonym ->
                antonym.antonym = entityManager.find(Meaning::class.java, antonym.antonym.id)
                entityManager.persist(antonym)
            }

            meaning.synonyms.forEach { synonym ->
                synonym.synonym = entityManager.find(Meaning::class.java, synonym.synonym.id)
                entityManager.persist(synonym)
            }

            entityManager.persist(meaning)
        }
        return entry
    }
}