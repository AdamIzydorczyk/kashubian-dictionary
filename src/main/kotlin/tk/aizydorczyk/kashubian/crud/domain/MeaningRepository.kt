package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation.SUPPORTS
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import javax.persistence.EntityManager

@Repository
@Transactional(propagation = SUPPORTS)
class MeaningRepository(val entityManager: EntityManager) {

    fun deleteMeaningById(meaningId: Long) {
        entityManager.createQuery("delete from Meaning where id = :$MEANING_ID")
            .setParameter(MEANING_ID, meaningId)
            .executeUpdate()
    }

    fun existsMeaningById(meaningId: Long): Boolean =
        entityManager.createQuery("select 1 from Meaning m where m.id = :$MEANING_ID")
            .setParameter(MEANING_ID, meaningId)
            .setMaxResults(1)
            .resultList
            .isNotEmpty()

    fun existsAntonymById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Antonym a where a.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteAntonymById(id: Long) {
        entityManager.createQuery("delete from Antonym where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsSynonymById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Synonym s where s.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteSynonymById(id: Long) {
        entityManager.createQuery("delete from Synonym where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsExampleById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Example e where e.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteExampleById(id: Long) {
        entityManager.createQuery("delete from Example where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsIdiomById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Idiom i where i.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteIdiomById(id: Long) {
        entityManager.createQuery("delete from Idiom where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsProverbById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Proverb p where p.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteProverbById(id: Long) {
        entityManager.createQuery("delete from Proverb where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsQuoteById(id: Long): Boolean =
        entityManager.createQuery("select 1 from Quote q where q.id = :id")
            .setParameter("id", id).setMaxResults(1).resultList.isNotEmpty()

    fun deleteQuoteById(id: Long) {
        entityManager.createQuery("delete from Quote where id = :id")
            .setParameter("id", id).executeUpdate()
    }

    fun existsTranslationByMeaningId(meaningId: Long): Boolean =
        entityManager.createQuery("select 1 from Translation t where t.id = :id")
            .setParameter("id", meaningId).setMaxResults(1).resultList.isNotEmpty()

    fun deleteTranslationByMeaningId(meaningId: Long) {
        entityManager.createQuery("delete from Translation where id = :id")
            .setParameter("id", meaningId).executeUpdate()
    }
}
