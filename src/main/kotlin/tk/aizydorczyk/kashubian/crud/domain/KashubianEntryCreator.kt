package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.extension.stripAccents
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import tk.aizydorczyk.kashubian.crud.model.entity.Variation
import javax.persistence.EntityManager

@Component
class KashubianEntryCreator(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun create(entry: KashubianEntry): KashubianEntry {
        entry.normalizedWord = entry.word?.stripAccents()

        entry.others.forEach(entityManager::persist)

        entry.meanings.forEach { meaning ->
            meaning.proverbs.forEach(entityManager::persist)
            meaning.phrasalVerbs.forEach(entityManager::persist)
            meaning.quotes.forEach(entityManager::persist)
            meaning.examples.forEach(entityManager::persist)
            meaning.antonyms.forEach(entityManager::persist)
            meaning.synonyms.forEach(entityManager::persist)
            entityManager.persist(meaning)

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
            entityManager.persist(this)
            variation?.let {
                entityManager.persist(Variation(id, it.variation, id))
            }
        }
    }

}
