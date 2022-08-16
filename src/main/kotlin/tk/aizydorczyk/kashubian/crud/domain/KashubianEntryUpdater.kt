package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import tk.aizydorczyk.kashubian.crud.model.entity.Variation
import javax.persistence.EntityManager

@Component
class KashubianEntryUpdater(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun update(entryId: Long, newEntry: KashubianEntry): KashubianEntry {
        val oldEntry = entityManager.find(KashubianEntry::class.java, entryId)

        oldEntry.others.zip(newEntry.others).onEach { it.first.id = it.second.id }.map { it.first }
            .forEach(entityManager::merge)
        newEntry.others.filter { it.id == 0L }.forEach(entityManager::persist)

        newEntry.variation = newEntry.variation?.let {
            entityManager.merge(Variation(entryId, it.variation, entryId))
        }

        oldEntry.meanings.onEach { meaning ->
        }.zip(newEntry.meanings)
            .onEach { meaningPair ->
                val newMeaning = meaningPair.first
                val oldMeaning = meaningPair.second
                oldMeaning.id = newMeaning.id

                oldMeaning.quotes.zip(newMeaning.quotes).onEach { it.first.id = it.second.id }.map { it.first }
                    .forEach(entityManager::merge)
                oldMeaning.antonyms.zip(newMeaning.antonyms).onEach { it.first.id = it.second.id }.map { it.first }
                    .forEach(entityManager::merge)
                oldMeaning.synonyms.zip(newMeaning.synonyms).onEach { it.first.id = it.second.id }.map { it.first }
                    .forEach(entityManager::merge)
                oldMeaning.examples.zip(newMeaning.examples).onEach { it.first.id = it.second.id }.map { it.first }
                    .forEach(entityManager::merge)
                oldMeaning.proverbs.zip(newMeaning.proverbs).onEach { it.first.id = it.second.id }.map { it.first }
                    .forEach(entityManager::merge)
                oldMeaning.phrasalVerbs.zip(newMeaning.phrasalVerbs).onEach { it.first.id = it.second.id }
                    .map { it.first }.forEach(entityManager::merge)
                newMeaning.translation = newMeaning.translation?.let {
                    entityManager.merge(Translation(id = oldMeaning.id,
                            polish = it.polish,
                            english = it.english,
                            german = it.german,
                            ukrainian = it.ukrainian,
                            meaning = oldMeaning.id))
                }

                newMeaning.quotes.filter { it.id == 0L }.forEach(entityManager::persist)
                newMeaning.antonyms.filter { it.id == 0L }.forEach(entityManager::persist)
                newMeaning.synonyms.filter { it.id == 0L }.forEach(entityManager::persist)
                newMeaning.examples.filter { it.id == 0L }.forEach(entityManager::persist)
                newMeaning.proverbs.filter { it.id == 0L }.forEach(entityManager::persist)
                newMeaning.phrasalVerbs.filter { it.id == 0L }.forEach(entityManager::persist)
            }

        newEntry.id = entryId
        return entityManager.merge(newEntry)
    }
}