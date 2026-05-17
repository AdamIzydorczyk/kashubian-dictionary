package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateAntonymEvent(
    val antonymId: Long,
    val antonymDto: AntonymDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_ANTONYM"
}
