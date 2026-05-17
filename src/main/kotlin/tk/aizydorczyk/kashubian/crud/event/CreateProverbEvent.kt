package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateProverbEvent(
    val meaningId: Long,
    val proverbDto: ProverbDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_PROVERB"
}
