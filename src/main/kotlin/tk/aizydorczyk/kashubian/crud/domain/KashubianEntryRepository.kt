package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation.SUPPORTS
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.BaseEntity
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.DEFAULT_ENTITY_MANAGER
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import java.math.BigInteger
import javax.persistence.EntityManager
import javax.persistence.ParameterMode

@Repository
@Transactional(propagation = SUPPORTS)
class KashubianEntryRepository(@Qualifier(DEFAULT_ENTITY_MANAGER) val entityManager: EntityManager) {

    fun deleteEntryById(entryId: Long) {
        entityManager.createQuery("delete from KashubianEntry where id = :$ENTRY_ID")
            .setParameter(ENTRY_ID, entryId)
            .executeUpdate()
    }

    fun findSoundFileByEntryId(entryId: Long): SoundFile =
        entityManager.createQuery("select f from SoundFile f where f.kashubianEntry = :$ENTRY_ID",
                SoundFile::class.java)
            .setParameter(ENTRY_ID, entryId)
            .singleResult

    fun removeSoundFileByEntryId(entryId: Long) {
        entityManager.createQuery("delete from SoundFile where kashubianEntry = :$ENTRY_ID")
            .setParameter(ENTRY_ID, entryId)
            .executeUpdate()
    }

    fun findHyponymsIds(meaningId: Long): List<MeaningHierarchyElement> =
        findMeaningHierarchyElementByProcedure(meaningId, "find_hyponym_ids")

    fun findHyperonymsIds(meaningId: Long): List<MeaningHierarchyElement> =
        findMeaningHierarchyElementByProcedure(meaningId, "find_hyperonyms_ids")

    fun findBases(entryId: Long): List<EntryHierarchyElement> =
        findEntryHierarchyElementByProcedure(entryId, "find_bases")

    fun findDerivatives(entryId: Long): List<EntryHierarchyElement> =
        findEntryHierarchyElementByProcedure(entryId, "find_derivatives")

    fun existsEntryById(entryId: Long): Boolean =
        entityManager.createQuery("select 1 from KashubianEntry e where e.id = :$ENTRY_ID")
            .setParameter(ENTRY_ID, entryId)
            .setMaxResults(1)
            .resultList
            .isNotEmpty()

    fun existsMeaningById(meaningId: Long): Boolean =
        entityManager.createQuery("select 1 from Meaning m where m.id = :$MEANING_ID")
            .setParameter(MEANING_ID, meaningId)
            .setMaxResults(1)
            .resultList
            .isNotEmpty()

    fun notExistsEntriesByNormalizedWordExcludeEntryId(entryId: Long, word: String): Boolean =
        entityManager.createQuery("select 1 from KashubianEntry e where e.normalizedWord = :word and e.id != :$ENTRY_ID")
            .setParameter("word", word)
            .setParameter(ENTRY_ID, entryId)
            .setMaxResults(1)
            .resultList
            .isEmpty()

    fun notExistsEntriesByNormalizedWord(word: String): Boolean =
        entityManager.createQuery("select 1 from KashubianEntry e where e.normalizedWord = :word")
            .setParameter("word", word)
            .setMaxResults(1)
            .resultList
            .isEmpty()

    fun notExistsMeaningByEntryIdAndMeaningId(entryId: Long, meaningId: Long): Boolean =
        entityManager.createQuery("select 1 from Meaning m where m.id = :$MEANING_ID and m.kashubianEntry = :$ENTRY_ID")
            .setParameter(MEANING_ID, meaningId)
            .setParameter(ENTRY_ID, entryId)
            .setMaxResults(1)
            .resultList
            .isEmpty()

    fun <EntityType : BaseEntity> findByTypeAndIds(type: Class<EntityType>, ids: List<Number>): List<EntityType> =
        entityManager.createQuery("select e from ${type.simpleName} e where e.id in (:ids)",
                type).setParameter("ids", ids).resultList

    fun countAllEntries(): Long = entityManager.createQuery("select count(e) from KashubianEntry e",
            Long::class.javaObjectType)
        .singleResult

    private fun findMeaningHierarchyElementByProcedure(meaningId: Long,
        procedureName: String): List<MeaningHierarchyElement> =
        executeProcedure(meaningId, procedureName).map { it as Array<*> }
            .map {
                MeaningHierarchyElement(meaningId = (it[0] as BigInteger).toLong(),
                        definition = it[1] as String,
                        entryId = (it[2] as BigInteger).toLong(),
                        word = it[3] as String)
            }

    private fun findEntryHierarchyElementByProcedure(entryId: Long,
        procedureName: String): List<EntryHierarchyElement> = executeProcedure(entryId, procedureName)
        .map { it as Array<*> }
        .map {
            EntryHierarchyElement(entryId = (it[0] as BigInteger).toLong(),
                    word = it[1] as String)
        }

    private fun executeProcedure(id: Long, procedureName: String): MutableList<Any?> =
        with(entityManager.createStoredProcedureQuery(procedureName)) {
            registerStoredProcedureParameter(
                    1,
                    Void.TYPE,
                    ParameterMode.REF_CURSOR
            )
            registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
            setParameter(2, id)
            execute()

            return resultList
        }

    fun findRandomWordOfTheDay(seed: Double): List<WordOfTheDayProjection> =
        with(entityManager.createStoredProcedureQuery("find_word_of_the_day")) {
            registerStoredProcedureParameter(
                    1,
                    Void.TYPE,
                    ParameterMode.REF_CURSOR
            )
            registerStoredProcedureParameter(2, Double::class.java, ParameterMode.IN)
            setParameter(2, seed)
            execute()

            return resultList.map { it as Array<*> }
                .map {
                    WordOfTheDayProjection(entryId = (it[0] as BigInteger).toLong(),
                            word = it[1] as String,
                            definition = it[2] as String)
                }
        }
}

data class WordOfTheDayProjection(val entryId: Long, val word: String, val definition: String)
data class MeaningHierarchyElement(val meaningId: Long, val definition: String, val entryId: Long, val word: String)
data class EntryHierarchyElement(val entryId: Long, val word: String)