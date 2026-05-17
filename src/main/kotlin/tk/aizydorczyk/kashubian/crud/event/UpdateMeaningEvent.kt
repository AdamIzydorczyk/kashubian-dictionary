package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateMeaningEvent(
    val meaningId: Long,
    val meaningDto: MeaningDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_MEANING"
}
