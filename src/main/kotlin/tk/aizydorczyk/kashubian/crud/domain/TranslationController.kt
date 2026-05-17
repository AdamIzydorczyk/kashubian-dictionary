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
import tk.aizydorczyk.kashubian.crud.event.CreateTranslationEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteTranslationEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateTranslationEvent
import tk.aizydorczyk.kashubian.crud.model.dto.TranslationDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.TRANSLATION_PATH
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Translation")
class TranslationController(
    private val mapper: MeaningMapper,
    private val creator: TranslationCreator,
    private val updater: TranslationUpdater,
    private val remover: TranslationRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + TRANSLATION_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody translationDto: TranslationDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Translation creating under meaning $meaningId with payload: $translationDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateTranslationEvent(meaningId, translationDto, auditingInformation))
            creator.create(meaningId, mapper.toEntity(translationDto))
        }.id
    }

    @PutMapping(MEANING_PATH + MEANING_ID_PATH + TRANSLATION_PATH)
    fun update(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody translationDto: TranslationDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Translation for meaning $meaningId updating with payload: $translationDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateTranslationEvent(meaningId, translationDto, auditingInformation))
            updater.update(meaningId, mapper.toEntity(translationDto))
        }.id
    }

    @DeleteMapping(MEANING_PATH + MEANING_ID_PATH + TRANSLATION_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Translation for meaning $meaningId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteTranslationEvent(meaningId, auditingInformation))
            remover.remove(meaningId)
        }
    }
}
