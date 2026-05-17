package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateOtherEvent(
    val entryId: Long,
    val otherDto: OtherDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_OTHER"
}
