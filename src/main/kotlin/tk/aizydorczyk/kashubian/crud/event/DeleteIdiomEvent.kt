package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class DeleteIdiomEvent(
    val idiomId: Long,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "DELETE_IDIOM"
}
