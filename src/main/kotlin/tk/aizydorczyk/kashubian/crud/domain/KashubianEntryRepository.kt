package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
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
@Transactional(propagation = Propagation.SUPPORTS)
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

    fun findHyponymsIds(meaningId: Long): List<BigInteger> =
        findByMeaningHierarchyElementByProcedure(meaningId, "find_hyponym_ids")

    fun findHyperonymsIds(meaningId: Long): List<BigInteger> =
        findByMeaningHierarchyElementByProcedure(meaningId, "find_hyperonyms_ids")

    fun findBaseMeaningsIds(meaningId: Long): List<BigInteger> =
        findByMeaningHierarchyElementByProcedure(meaningId, "find_base_meanings_ids")

    fun findDerivativeMeaningsIds(meaningId: Long): List<BigInteger> =
        findByMeaningHierarchyElementByProcedure(meaningId, "find_derivative_meanings_ids")

    fun countEntriesById(entryId: Long): Long =
        entityManager.createQuery("select count(e) from KashubianEntry e where e.id = :$ENTRY_ID",
                Long::class.javaObjectType)
            .setParameter(ENTRY_ID, entryId)
            .singleResult

    fun countMeaningsById(meaningId: Long): Long =
        entityManager.createQuery("select count(m) from Meaning m where m.id = :$MEANING_ID",
                Long::class.javaObjectType)
            .setParameter(MEANING_ID, meaningId).singleResult

    fun countEntriesByNormalizedWordExcludeEntryId(entryId: Long, word: String): Long =
        entityManager.createQuery("select count(e) from KashubianEntry e where e.normalizedWord = :word and e.id != :$ENTRY_ID",
                Long::class.javaObjectType)
            .setParameter("word", word)
            .setParameter(ENTRY_ID, entryId)
            .singleResult

    fun countEntriesByNormalizedWord(word: String): Long =
        entityManager.createQuery("select count(e) from KashubianEntry e where e.normalizedWord = :word",
                Long::class.javaObjectType)
            .setParameter("word", word)
            .singleResult

    fun <EntityType : BaseEntity> findByTypeAndIds(type: Class<EntityType>, ids: List<Number>): List<EntityType> =
        entityManager.createQuery("select e from ${type.simpleName} e where e.id in (:ids)",
                type).setParameter("ids", ids).resultList

    fun countAllEntries(): Long = entityManager.createQuery("select count(e) from KashubianEntry e",
            Long::class.javaObjectType)
        .singleResult

    private fun findByMeaningHierarchyElementByProcedure(meaningId: Long, procedureName: String): List<BigInteger> =
        with(entityManager.createStoredProcedureQuery(procedureName)) {
            registerStoredProcedureParameter(
                    1,
                    Void.TYPE,
                    ParameterMode.REF_CURSOR
            )
            registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
            setParameter(2, meaningId)
            execute()

            return resultList.map { it as BigInteger }
        }

    fun findAllPrioritizedEntryIds(): List<WordOfTheDayProjection> =
        entityManager.createQuery("""
            |select new tk.aizydorczyk.kashubian.crud.domain.WordOfTheDayProjection(e.id, e.word, m.definition) 
            |from KashubianEntry e 
            |join e.meanings m
            |where e.priority = true""".trimMargin(),
                WordOfTheDayProjection::class.java)
            .resultList

}

data class WordOfTheDayProjection(val id: Long, val word: String, val definition: String)