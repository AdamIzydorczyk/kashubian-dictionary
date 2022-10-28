package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateEntryEvent(
    val entryDto: KashubianEntryDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType(): String = "CREATE_ENTRY"
}