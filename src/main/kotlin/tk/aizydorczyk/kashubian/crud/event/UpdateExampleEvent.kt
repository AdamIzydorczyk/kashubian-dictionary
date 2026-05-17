package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.ExampleDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateExampleEvent(
    val exampleId: Long,
    val exampleDto: ExampleDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_EXAMPLE"
}
