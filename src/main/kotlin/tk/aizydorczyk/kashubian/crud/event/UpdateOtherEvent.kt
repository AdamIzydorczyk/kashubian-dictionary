package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateOtherEvent(
    val otherId: Long,
    val otherDto: OtherDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_OTHER"
}
