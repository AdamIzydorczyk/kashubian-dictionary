package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateEntryEvent(
    val entryId: Long,
    val entryDto: KashubianEntryDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType(): String = "UPDATE_ENTRY"
}