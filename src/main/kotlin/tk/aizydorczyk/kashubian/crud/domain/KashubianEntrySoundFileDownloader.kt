package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import javax.persistence.EntityManager

@Component
class KashubianEntrySoundFileDownloader(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {

    @Transactional(readOnly = true)
    fun download(entryId: Long): SoundFile =
        entityManager.createQuery("select f from SoundFile f where f.kashubianEntry = :entryId", SoundFile::class.java)
            .setParameter("entryId", entryId)
            .singleResult

}
