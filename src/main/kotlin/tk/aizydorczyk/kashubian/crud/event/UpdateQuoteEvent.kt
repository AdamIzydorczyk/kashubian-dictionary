package tk.aizydorczyk.kashubian.crud.event

import tk.aizydorczyk.kashubian.crud.model.dto.QuoteDto
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation

data class UpdateQuoteEvent(
    val quoteId: Long,
    val quoteDto: QuoteDto,
    override val auditingInformation: AuditingInformation) : KashubianEntryApplicationEvent {
    override fun eventType() = "UPDATE_QUOTE"
}
