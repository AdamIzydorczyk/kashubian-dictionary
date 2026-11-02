package tk.aizydorczyk.kashubian.crud.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation.SUPPORTS
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.BaseEntity
import tk.aizydorczyk.kashubian.crud.model.entity.Event
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import java.math.BigInteger
import javax.persistence.EntityManager
import javax.persistence.ParameterMode

@Repository
@Transactional(propagation = SUPPORTS)
class KashubianEntryRepository(val entityManager: EntityManager,
    val objectMapper: ObjectMapper) {

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

    fun findMeaningIdsByEntryId(entryId: Long): List<Long> =
            entityManager.createQuery("select m.id from Meaning m where m.kashubianEntry = :$ENTRY_ID",
                    Long::class.javaObjectType)
                    .setParameter(ENTRY_ID, entryId)
                    .resultList

    fun findHyponyms(meaningId: Long): List<MeaningHierarchyElement> =
            findMeaningHierarchyElementByProcedure(meaningId, "find_hyponyms")

    fun findHyperonyms(meaningId: Long): List<MeaningHierarchyElement> =
            findMeaningHierarchyElementByProcedure(meaningId, "find_hyperonyms")

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

    fun notExistsEntriesByWordExcludeEntryId(entryId: Long, word: String): Boolean =
            entityManager.createQuery("select 1 from KashubianEntry e where e.word = :word and e.id != :$ENTRY_ID")
                    .setParameter("word", word)
                    .setParameter(ENTRY_ID, entryId)
                    .setMaxResults(1)
                    .resultList
                    .isEmpty()

    fun notExistsEntriesByWord(word: String): Boolean =
            entityManager.createQuery("select 1 from KashubianEntry e where e.word = :word")
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

    fun existsFileByEntryId(entryId: Long): Boolean =
            entityManager.createQuery("select 1 from SoundFile f where f.kashubianEntry = :$ENTRY_ID")
                    .setParameter(ENTRY_ID, entryId)
                    .setMaxResults(1)
                    .resultList
                    .isNotEmpty()

    fun <EntityType : BaseEntity> findByTypeAndIds(type: Class<EntityType>, ids: List<Number>): List<EntityType> =
            entityManager.createQuery("select e from ${type.simpleName} e where e.id in (:ids)",
                    type).setParameter("ids", ids).resultList

    fun countAllEntries(): Long = entityManager.createQuery("select count(e) from KashubianEntry e",
            Long::class.javaObjectType)
            .singleResult

    fun countEvents(): Long = entityManager.createQuery("select count(e) from Event e",
            Long::class.javaObjectType)
            .singleResult

    fun findAllEvents(): List<Event> =
            entityManager.createQuery("select e from Event e order by e.id",
                    Event::class.java)
                    .resultList

    private fun findMeaningHierarchyElementByProcedure(meaningId: Long,
                                                       procedureName: String): List<MeaningHierarchyElement> =
            executeProcedure(meaningId, procedureName)?.let {
                objectMapper.readValue(it,
                        object : TypeReference<List<MeaningHierarchyElement>>() {})
            } ?: emptyList()

    private fun findEntryHierarchyElementByProcedure(entryId: Long,
                                                     procedureName: String): List<EntryHierarchyElement> = executeProcedure(entryId, procedureName)
            ?.let {
                objectMapper.readValue(it,
                        object : TypeReference<List<EntryHierarchyElement>>() {})
            } ?: emptyList()

    private fun executeProcedure(id: Long, procedureName: String): String? =
            entityManager.createNativeQuery("select CAST(j AS text) from $procedureName($id) as j").singleResult as String?

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
data class MeaningHierarchyElement(@JsonProperty("meaning_id") val meaningId: Long,
    val definition: String,
    @JsonProperty("entry_id") val entryId: Long,
    val word: String)

data class EntryHierarchyElement(@JsonProperty("entry_id") val entryId: Long, val word: String)