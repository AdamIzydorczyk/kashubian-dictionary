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
import tk.aizydorczyk.kashubian.crud.event.CreateSynonymEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteSynonymEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateSynonymEvent
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.SYNONYM_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.SYNONYM_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.SYNONYM_PATH
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.crud.validator.SynonymExists
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Synonym")
class SynonymController(
    private val mapper: MeaningMapper,
    private val creator: SynonymCreator,
    private val updater: SynonymUpdater,
    private val remover: SynonymRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + SYNONYM_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody synonymDto: SynonymDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Synonym creating under meaning $meaningId with payload: $synonymDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateSynonymEvent(meaningId, synonymDto, auditingInformation))
            creator.create(meaningId, mapper.synonymDtoToSynonym(synonymDto))
        }.id
    }

    @PutMapping(SYNONYM_PATH + SYNONYM_ID_PATH)
    fun update(@SynonymExists @PathVariable(SYNONYM_ID) synonymId: Long,
        @Validated(OnUpdate::class) @RequestBody synonymDto: SynonymDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Synonym $synonymId updating with payload: $synonymDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateSynonymEvent(synonymId, synonymDto, auditingInformation))
            updater.update(synonymId, mapper.synonymDtoToSynonym(synonymDto))
        }.id
    }

    @DeleteMapping(SYNONYM_PATH + SYNONYM_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@SynonymExists @PathVariable(SYNONYM_ID) synonymId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Synonym $synonymId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteSynonymEvent(synonymId, auditingInformation))
            remover.remove(synonymId)
        }
    }
}
