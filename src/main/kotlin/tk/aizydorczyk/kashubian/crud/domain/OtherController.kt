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
import tk.aizydorczyk.kashubian.crud.event.CreateOtherEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteOtherEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateOtherEvent
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.KASHUBIAN_ENTRY_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.OTHER_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.OTHER_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.OTHER_PATH
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OtherExists
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Other")
class OtherController(
    private val mapper: KashubianEntryMapper,
    private val creator: OtherCreator,
    private val updater: OtherUpdater,
    private val remover: OtherRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH + OTHER_PATH)
    @ResponseStatus(CREATED)
    fun create(@EntryExists @PathVariable(ENTRY_ID) entryId: Long,
        @Validated(OnCreate::class) @RequestBody otherDto: OtherDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Other creating under entry $entryId with payload: $otherDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateOtherEvent(entryId, otherDto, auditingInformation))
            creator.create(entryId, mapper.otherDtoToOther(otherDto))
        }.id
    }

    @PutMapping(OTHER_PATH + OTHER_ID_PATH)
    fun update(@OtherExists @PathVariable(OTHER_ID) otherId: Long,
        @Validated(OnCreate::class) @RequestBody otherDto: OtherDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Other $otherId updating with payload: $otherDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateOtherEvent(otherId, otherDto, auditingInformation))
            updater.update(otherId, mapper.otherDtoToOther(otherDto))
        }.id
    }

    @DeleteMapping(OTHER_PATH + OTHER_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@OtherExists @PathVariable(OTHER_ID) otherId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Other $otherId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteOtherEvent(otherId, auditingInformation))
            remover.remove(otherId)
        }
    }
}
