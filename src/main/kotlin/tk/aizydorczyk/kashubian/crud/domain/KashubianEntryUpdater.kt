package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import tk.aizydorczyk.kashubian.crud.model.entity.Variation
import javax.persistence.EntityManager

@Component
class KashubianEntryUpdater(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun update(entryId: Long, newEntry: KashubianEntry): KashubianEntry {
        val oldEntry = entityManager.find(KashubianEntry::class.java, entryId)

        oldEntry.others.zip(newEntry.others)
            .onEach { it.first.id = it.second.id }
            .map { it.first }
            .forEach(entityManager::merge)

        newEntry.others.filter { it.id == 0L }
            .forEach(entityManager::persist)

        newEntry.variation = newEntry.variation?.let {
            entityManager.merge(Variation(entryId, it.variation, entryId))
        }

        oldEntry.meanings.onEach { meaning -> }
            .zip(newEntry.meanings)
            .onEach(::persistOrMergeMeaningElementsDependingOnIdExist)

        newEntry.id = entryId
        return entityManager.merge(newEntry)
    }

    private fun persistOrMergeMeaningElementsDependingOnIdExist(meaningPair: Pair<Meaning, Meaning>) {
        val newMeaning = meaningPair.first
        val oldMeaning = meaningPair.second
        oldMeaning.id = newMeaning.id

        overrideExistsMeaningElements(oldMeaning, newMeaning)

        newMeaning.translation = newMeaning.translation?.let {
            entityManager.merge(Translation(id = oldMeaning.id,
                    polish = it.polish,
                    english = it.english,
                    german = it.german,
                    ukrainian = it.ukrainian,
                    meaning = oldMeaning.id))
        }

        addNewMeaningElements(newMeaning)
    }

    private fun addNewMeaningElements(newMeaning: Meaning) {
        (newMeaning.quotes
                + newMeaning.antonyms
                + newMeaning.synonyms
                + newMeaning.examples
                + newMeaning.proverbs
                + newMeaning.phrasalVerbs).filter { it.id == 0L }
            .forEach(entityManager::persist)
    }

    private fun overrideExistsMeaningElements(oldMeaning: Meaning,
        newMeaning: Meaning) {
        (oldMeaning.quotes.zip(newMeaning.quotes)
                + oldMeaning.antonyms.zip(newMeaning.antonyms)
                + oldMeaning.synonyms.zip(newMeaning.synonyms)
                + oldMeaning.examples.zip(newMeaning.examples)
                + oldMeaning.proverbs.zip(newMeaning.proverbs)
                + oldMeaning.phrasalVerbs.zip(newMeaning.phrasalVerbs)).onEach { it.first.id = it.second.id }
            .map { it.first }
            .forEach(entityManager::merge)
    }
}