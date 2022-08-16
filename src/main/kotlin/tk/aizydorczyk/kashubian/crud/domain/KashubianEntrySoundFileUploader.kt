package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import javax.persistence.EntityManager

@Component
class KashubianEntrySoundFileUploader(@Qualifier("defaultEntityManager") val entityManager: EntityManager) {
    @Transactional
    fun upload(entryId: Long, soundFile: MultipartFile) {
        val entry = entityManager.find(KashubianEntry::class.java, entryId)
        SoundFile(
                id = entryId,
                fileName = soundFile.originalFilename.toString(),
                type = soundFile.contentType.toString(),
                file = soundFile.bytes,
                kashubianEntry = entryId).let {
            entityManager.merge(it)
            entityManager.flush()
            entry.soundFile = it
        }
    }
}