package tk.aizydorczyk.kashubian.crud.domain

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.event.CreateQuoteEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteQuoteEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateQuoteEvent
import tk.aizydorczyk.kashubian.crud.model.dto.QuoteDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.QUOTE_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.QUOTE_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.QUOTE_PATH
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.QuoteExists
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Quote")
class QuoteController(
    private val mapper: MeaningMapper,
    private val creator: QuoteCreator,
    private val updater: QuoteUpdater,
    private val remover: QuoteRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + QUOTE_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody quoteDto: QuoteDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Quote creating under meaning $meaningId with payload: $quoteDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateQuoteEvent(meaningId, quoteDto, auditingInformation))
            creator.create(meaningId, mapper.toEntity(quoteDto))
        }.id
    }

    @PutMapping(QUOTE_PATH + QUOTE_ID_PATH)
    fun update(@QuoteExists @PathVariable(QUOTE_ID) quoteId: Long,
        @Validated(OnCreate::class) @RequestBody quoteDto: QuoteDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Quote $quoteId updating with payload: $quoteDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateQuoteEvent(quoteId, quoteDto, auditingInformation))
            updater.update(quoteId, mapper.toEntity(quoteDto))
        }.id
    }

    @DeleteMapping(QUOTE_PATH + QUOTE_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@QuoteExists @PathVariable(QUOTE_ID) quoteId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Quote $quoteId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteQuoteEvent(quoteId, auditingInformation))
            remover.remove(quoteId)
        }
    }
}
