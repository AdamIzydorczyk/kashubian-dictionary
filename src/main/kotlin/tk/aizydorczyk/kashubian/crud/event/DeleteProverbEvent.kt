package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class DeleteProverbEvent(
    val proverbId: Long,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "DELETE_PROVERB"
}
