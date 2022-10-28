package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UploadSoundFileEvent(
    val entryId: Long,
    val originalFilename: String,
    val contentType: String,
    val encodedFile: String,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType(): String = "UPLOAD_SOUND_FILE"
}
