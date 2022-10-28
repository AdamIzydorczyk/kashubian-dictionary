package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

interface KashubianEntryApplicationEvent {
    val auditingInformation: AuditingInformation
    fun eventType(): String
}