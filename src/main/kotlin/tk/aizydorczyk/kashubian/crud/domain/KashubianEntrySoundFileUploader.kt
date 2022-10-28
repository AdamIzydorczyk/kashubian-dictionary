package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

class KashubianEntrySoundFileUploader(private val mergeFunction: (SoundFile) -> SoundFile) {
    fun upload(entryId: Long,
        originalFilename: String,
        contentType: String,
        fileBytes: ByteArray,
        auditingInformation: AuditingInformation) {
        SoundFile(
                id = entryId,
                createdBy = auditingInformation.userName,
                createdAt = auditingInformation.executionTime,
                fileName = originalFilename,
                type = contentType,
                file = fileBytes,
                kashubianEntry = entryId).let {
            mergeFunction.invoke(it)
        }
    }
}