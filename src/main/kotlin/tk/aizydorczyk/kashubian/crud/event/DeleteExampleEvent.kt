package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class DeleteExampleEvent(
    val exampleId: Long,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "DELETE_EXAMPLE"
}
