package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateMeaningEvent(
    val meaningDto: MeaningDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_MEANING"
}
