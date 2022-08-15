package tk.aizydorczyk.kashubian.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class KashubianEntryRemover(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {

    @Transactional
    fun remove(entryId: Long) {
        entityManager.createQuery("delete from KashubianEntry where id = :entryId")
            .setParameter("entryId", entryId)
            .executeUpdate()
    }
}