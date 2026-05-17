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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.event.CreateMeaningEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteMeaningEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateMeaningEvent
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@RequestMapping(MEANING_PATH)
@Validated
@Tag(name = "Meaning")
class MeaningController(
    private val meaningMapper: MeaningMapper,
    private val creator: MeaningCreator,
    private val updater: MeaningUpdater,
    private val remover: MeaningRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated(OnCreate::class) @RequestBody meaningDto: MeaningDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Meaning creating with payload: $meaningDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateMeaningEvent(meaningDto, auditingInformation))
            creator.create(meaningMapper.toEntity(meaningDto), meaningDto.kashubianEntryIds)
        }.id
    }

    @PutMapping(MEANING_ID_PATH)
    fun update(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnUpdate::class) @RequestBody meaningDto: MeaningDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Meaning id: $meaningId updating with payload: $meaningDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateMeaningEvent(meaningId, meaningDto, auditingInformation))
            updater.update(meaningId, meaningMapper.toEntity(meaningDto), meaningDto.kashubianEntryIds)
        }.id
    }

    @DeleteMapping(MEANING_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Meaning id: $meaningId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteMeaningEvent(meaningId, auditingInformation))
            remover.remove(meaningId)
        }
    }
}
