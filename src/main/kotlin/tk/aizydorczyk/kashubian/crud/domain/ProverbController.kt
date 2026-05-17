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
import tk.aizydorczyk.kashubian.crud.event.CreateProverbEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteProverbEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateProverbEvent
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.PROVERB_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.PROVERB_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.PROVERB_PATH
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.ProverbExists
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Proverb")
class ProverbController(
    private val mapper: MeaningMapper,
    private val creator: ProverbCreator,
    private val updater: ProverbUpdater,
    private val remover: ProverbRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + PROVERB_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody proverbDto: ProverbDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Proverb creating under meaning $meaningId with payload: $proverbDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateProverbEvent(meaningId, proverbDto, auditingInformation))
            creator.create(meaningId, mapper.toEntity(proverbDto))
        }.id
    }

    @PutMapping(PROVERB_PATH + PROVERB_ID_PATH)
    fun update(@ProverbExists @PathVariable(PROVERB_ID) proverbId: Long,
        @Validated(OnCreate::class) @RequestBody proverbDto: ProverbDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Proverb $proverbId updating with payload: $proverbDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateProverbEvent(proverbId, proverbDto, auditingInformation))
            updater.update(proverbId, mapper.toEntity(proverbDto))
        }.id
    }

    @DeleteMapping(PROVERB_PATH + PROVERB_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@ProverbExists @PathVariable(PROVERB_ID) proverbId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Proverb $proverbId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteProverbEvent(proverbId, auditingInformation))
            remover.remove(proverbId)
        }
    }
}
