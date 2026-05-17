package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.IdiomDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateIdiomEvent(
    val meaningId: Long,
    val idiomDto: IdiomDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_IDIOM"
}
