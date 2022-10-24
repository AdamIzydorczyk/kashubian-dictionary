package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.ChildEntity
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.entity.Translation
import javax.persistence.EntityManager

@Component
class KashubianEntryUpdater(val entityManager: EntityManager) {
    @Transactional
    fun update(entryId: Long, newEntry: KashubianEntry): KashubianEntry {
        newEntry.normalizedWord = newEntry.word?.normalize()

        val oldEntry = entityManager.find(KashubianEntry::class.java, entryId)

        addOrMergeElements(entryId, oldEntry.others, newEntry.others)
        addOrMergeElements(entryId, oldEntry.meanings, newEntry.meanings, ::processMeanings)

        newEntry.id = entryId
        entityManager.merge(newEntry)
        return newEntry
    }

    private fun processMeanings(oldMeaning: Meaning,
        newMeaning: Meaning) {
        if (oldMeaning.translation == null) {
            entityManager.find(Translation::class.java, newMeaning.id)?.let { entityManager.remove(it) }
            newMeaning.translation?.let {
                newMeaning.translation = entityManager.merge(it.copyWitchNormalizedFieldsAndAssignedId(newMeaning.id))
            }
        } else {
            newMeaning.translation?.let {
                newMeaning.translation = entityManager.merge(it.copyWitchNormalizedFieldsAndAssignedId(newMeaning.id))
            }
        }

        addOrMergeElements(oldMeaning.id, oldMeaning.quotes, newMeaning.quotes)
        addOrMergeElements(oldMeaning.id, oldMeaning.antonyms, newMeaning.antonyms)
        addOrMergeElements(oldMeaning.id, oldMeaning.synonyms, newMeaning.synonyms)
        addOrMergeElements(oldMeaning.id, oldMeaning.examples, newMeaning.examples)
        addOrMergeElements(oldMeaning.id, oldMeaning.proverbs, newMeaning.proverbs)
        addOrMergeElements(oldMeaning.id, oldMeaning.idioms, newMeaning.idioms)
    }

    fun <EntityType : ChildEntity> addOrMergeElements(parentId: Long,
        old: MutableList<EntityType>,
        new: MutableList<EntityType>,
        customFieldsFunction: (EntityType, EntityType) -> Unit = { _: EntityType, _: EntityType -> }) {
        if (old.size > new.size) {
            mergeWithOldAndRemoveRedundant(old, new, parentId, customFieldsFunction)
        } else if (old.size == new.size) {
            mergeAll(old, new, parentId, customFieldsFunction)
        } else {
            mergeWithOldAndAddNew(old, new, parentId, customFieldsFunction)
        }
        entityManager.flush()
    }

    private fun <EntityType : ChildEntity> mergeWithOldAndAddNew(old: MutableList<EntityType>,
        new: MutableList<EntityType>,
        parentId: Long,
        customFieldsFunction: (EntityType, EntityType) -> Unit) {
        old.zip(new.subList(0, old.size)).onEach { oldAndNew ->
            oldAndNew.second.id = oldAndNew.first.id
            oldAndNew.second.setParentId(parentId)
            customFieldsFunction.invoke(oldAndNew.first, oldAndNew.second)
            entityManager.merge(oldAndNew.second)
        }
        new.subList(old.size, new.size).onEach { entity ->
            entity.setParentId(parentId)
        }.onEach(entityManager::persist)
            .forEach { entity ->
                customFieldsFunction.invoke(entity, entity)
            }
    }

    private fun <EntityType : ChildEntity> mergeAll(old: MutableList<EntityType>,
        new: MutableList<EntityType>,
        parentId: Long,
        customFieldsFunction: (EntityType, EntityType) -> Unit) {
        old.zip(new).onEach {
            it.second.id = it.first.id
            it.second.setParentId(parentId)
            customFieldsFunction.invoke(it.first, it.second)
            entityManager.merge(it.second)
        }
    }

    private fun <EntityType : ChildEntity> mergeWithOldAndRemoveRedundant(old: MutableList<EntityType>,
        new: MutableList<EntityType>,
        parentId: Long,
        customFieldsFunction: (EntityType, EntityType) -> Unit) {
        old.subList(0, new.size).zip(new).onEach { oldAndNew ->
            oldAndNew.second.id = oldAndNew.first.id
            oldAndNew.second.setParentId(parentId)
            customFieldsFunction.invoke(oldAndNew.first, oldAndNew.second)
            entityManager.merge(oldAndNew.second)
        }
        old.subList(new.size, old.size)
            .onEach(entityManager::remove)
            .let(old::removeAll)
    }

}