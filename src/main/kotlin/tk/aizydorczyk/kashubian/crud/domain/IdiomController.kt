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
import tk.aizydorczyk.kashubian.crud.event.CreateIdiomEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteIdiomEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateIdiomEvent
import tk.aizydorczyk.kashubian.crud.model.dto.IdiomDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.IDIOM_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.IDIOM_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.IDIOM_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.validator.IdiomExists
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Idiom")
class IdiomController(
    private val mapper: MeaningMapper,
    private val creator: IdiomCreator,
    private val updater: IdiomUpdater,
    private val remover: IdiomRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + IDIOM_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody idiomDto: IdiomDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Idiom creating under meaning $meaningId with payload: $idiomDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateIdiomEvent(meaningId, idiomDto, auditingInformation))
            creator.create(meaningId, mapper.toEntity(idiomDto))
        }.id
    }

    @PutMapping(IDIOM_PATH + IDIOM_ID_PATH)
    fun update(@IdiomExists @PathVariable(IDIOM_ID) idiomId: Long,
        @Validated(OnCreate::class) @RequestBody idiomDto: IdiomDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Idiom $idiomId updating with payload: $idiomDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateIdiomEvent(idiomId, idiomDto, auditingInformation))
            updater.update(idiomId, mapper.toEntity(idiomDto))
        }.id
    }

    @DeleteMapping(IDIOM_PATH + IDIOM_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@IdiomExists @PathVariable(IDIOM_ID) idiomId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Idiom $idiomId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteIdiomEvent(idiomId, auditingInformation))
            remover.remove(idiomId)
        }
    }
}
