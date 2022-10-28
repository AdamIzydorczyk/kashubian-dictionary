package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class DeleteSoundFileEvent(
    val entryId: Long,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType(): String = "DELETE_SOUND_FILE"
}
