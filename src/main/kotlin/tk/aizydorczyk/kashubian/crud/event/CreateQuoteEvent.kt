package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.QuoteDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class CreateQuoteEvent(
    val meaningId: Long,
    val quoteDto: QuoteDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "CREATE_QUOTE"
}
