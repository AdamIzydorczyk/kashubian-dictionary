package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateAntonymEvent(
    val meaningId: Long,
    val antonymDto: AntonymDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_ANTONYM"
}
