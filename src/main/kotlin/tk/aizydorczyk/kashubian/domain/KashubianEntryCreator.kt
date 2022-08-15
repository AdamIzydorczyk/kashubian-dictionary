package tk.aizydorczyk.kashubian.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Translation
import tk.aizydorczyk.kashubian.domain.model.entity.Variation
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry {
        entry.others.forEach {
            it.other = entityManager.find(KashubianEntry::class.java, it.other.id)
            entityManager.persist(it)
        }
        entry.meanings.forEach { meaning ->
            meaning.proverbs.forEach(entityManager::persist)
            meaning.phrasalVerbs.forEach(entityManager::persist)
            meaning.quotes.forEach(entityManager::persist)
            meaning.examples.forEach(entityManager::persist)

            meaning.antonyms.forEach { antonym ->
                entityManager.persist(antonym)
            }

            meaning.synonyms.forEach { synonym ->
                entityManager.persist(synonym)
            }

            entityManager.persist(meaning)
            meaning.translation?.let {
                entityManager.merge(Translation(id = meaning.id,
                        polish = it.polish,
                        english = it.english,
                        german = it.german,
                        ukrainian = it.ukrainian,
                        meaning = meaning.id))
            }
        }
        return entry.apply {
            entityManager.persist(this)
            variation?.let {
                entityManager.merge(Variation(id, it.variation, id))
            }
        }
    }

}