package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationsConstants.Companion.DEFAULT_ENTITY_MANAGER
import javax.persistence.EntityManager

@Component
class KashubianEntrySoundFileUploader(@Qualifier(DEFAULT_ENTITY_MANAGER) val entityManager: EntityManager) {
    @Transactional
    fun upload(entryId: Long, soundFile: MultipartFile) {
        SoundFile(
                id = entryId,
                fileName = soundFile.originalFilename.toString(),
                type = soundFile.contentType.toString(),
                file = soundFile.bytes,
                kashubianEntry = entryId).let {
            entityManager.merge(it)
        }
    }
}