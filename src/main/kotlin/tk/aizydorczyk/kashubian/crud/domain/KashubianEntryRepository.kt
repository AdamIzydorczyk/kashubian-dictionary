package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.BaseEntity
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import java.math.BigInteger
import javax.persistence.EntityManager
import javax.persistence.ParameterMode

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
class KashubianEntryRepository(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {

    fun deleteEntryById(entryId: Long) {
        entityManager.createQuery("delete from KashubianEntry where id = :entryId")
            .setParameter("entryId", entryId)
            .executeUpdate()
    }

    fun findSoundFileByEntryId(entryId: Long): SoundFile =
        entityManager.createQuery("select f from SoundFile f where f.kashubianEntry = :entryId", SoundFile::class.java)
            .setParameter("entryId", entryId)
            .singleResult

    fun removeSoundFileByEntryId(entryId: Long) {
        entityManager.createQuery("delete from SoundFile where kashubianEntry = :entryId")
            .setParameter("entryId", entryId)
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
        entityManager.createQuery("select count(e) from KashubianEntry e where e.id = :id",
                Long::class.javaObjectType)
            .setParameter("id", entryId)
            .singleResult

    fun countMeaningsById(meaningId: Long): Long =
        entityManager.createQuery("select count(m) from Meaning m where m.id = :id",
                Long::class.javaObjectType)
            .setParameter("id", meaningId).singleResult

    fun countEntriesByWordExcludeEntryId(entryId: Long, word: String): Long =
        entityManager.createQuery("select count(e) from KashubianEntry e where e.word = :word and e.id != :id",
                Long::class.javaObjectType)
            .setParameter("word", word)
            .setParameter("id", entryId)
            .singleResult

    fun countEntriesByWord(word: String): Long =
        entityManager.createQuery("select count(e) from KashubianEntry e where e.word = :word",
                Long::class.javaObjectType)
            .setParameter("word", word)
            .singleResult

    fun <EntityType : BaseEntity> findByTypeAndIds(type: Class<EntityType>, ids: List<Number>): List<EntityType> =
        entityManager.createQuery("select e from ${type.simpleName} e where e.id in (:ids)",
                type).setParameter("ids", ids).resultList

    fun countAllEntries(): Long = entityManager.createQuery("select count(e) from KashubianEntry e",
            Long::class.javaObjectType)
        .singleResult

    private fun findByMeaningHierarchyElementByProcedure(meaningId: Long, procedureName: String): List<BigInteger> {
        val procedure =
            entityManager.createStoredProcedureQuery(procedureName)
        procedure.registerStoredProcedureParameter(
                1,
                Void.TYPE,
                ParameterMode.REF_CURSOR
        )
        procedure.registerStoredProcedureParameter(2, Long::class.java, ParameterMode.IN)
        procedure.setParameter(2, meaningId)
        procedure.execute()

        return procedure.resultList.map { it as BigInteger }
    }

}