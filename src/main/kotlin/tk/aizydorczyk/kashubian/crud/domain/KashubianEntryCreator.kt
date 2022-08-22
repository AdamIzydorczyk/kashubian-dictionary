package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import tk.aizydorczyk.kashubian.crud.model.entity.Variation
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry {
        entry.normalizedWord = entry.word?.normalize()
        entityManager.persist(entry)

        entry.others.onEach { it.kashubianEntry = entry.id }.forEach(entityManager::persist)

        entry.meanings.forEach { meaning ->
            meaning.apply { kashubianEntry = entry.id }
            entityManager.persist(meaning)

            meaning.proverbs.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)
            meaning.phrasalVerbs.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)
            meaning.quotes.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)
            meaning.examples.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)
            meaning.antonyms.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)
            meaning.synonyms.onEach { it.meaning = meaning.id }.forEach(entityManager::persist)

            meaning.translation?.let {
                entityManager.persist(Translation(id = meaning.id,
                        polish = it.polish,
                        english = it.english,
                        german = it.german,
                        ukrainian = it.ukrainian,
                        meaning = meaning.id))
            }
        }
        return entry.apply {
            variation?.let {
                entityManager.persist(Variation(id, it.variation, id))
            }
        }
    }

}
