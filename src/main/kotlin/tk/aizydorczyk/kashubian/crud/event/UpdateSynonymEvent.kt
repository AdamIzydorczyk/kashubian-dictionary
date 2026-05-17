package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateSynonymEvent(
    val synonymId: Long,
    val synonymDto: SynonymDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_SYNONYM"
}
