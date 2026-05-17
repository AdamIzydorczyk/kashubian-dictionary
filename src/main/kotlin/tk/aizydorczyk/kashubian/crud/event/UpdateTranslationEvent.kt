package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.TranslationDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateTranslationEvent(
    val meaningId: Long,
    val translationDto: TranslationDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_TRANSLATION"
}
