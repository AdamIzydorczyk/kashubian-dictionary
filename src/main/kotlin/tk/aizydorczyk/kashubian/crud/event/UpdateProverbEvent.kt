package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateProverbEvent(
    val proverbId: Long,
    val proverbDto: ProverbDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_PROVERB"
}
