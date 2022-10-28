package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import javax.persistence.EntityManager

@Component
class KashubianEntrySoundFileUploader(val entityManager: EntityManager) {
    @Transactional
    fun upload(entryId: Long, soundFile: MultipartFile, auditingInformation: AuditingInformation) {
        SoundFile(
                id = entryId,
                createdBy = auditingInformation.userName,
                createdAt = auditingInformation.executionTime,
                fileName = soundFile.originalFilename.toString(),
                type = soundFile.contentType.toString(),
                file = soundFile.bytes,
                kashubianEntry = entryId).let {
            entityManager.merge(it)
        }
    }
}