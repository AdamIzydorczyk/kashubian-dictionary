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
import tk.aizydorczyk.kashubian.crud.event.CreateAntonymEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteAntonymEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateAntonymEvent
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ANTONYM_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ANTONYM_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ANTONYM_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.validator.AntonymExists
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Antonym")
class AntonymController(
    private val mapper: MeaningMapper,
    private val creator: AntonymCreator,
    private val updater: AntonymUpdater,
    private val remover: AntonymRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + ANTONYM_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody antonymDto: AntonymDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Antonym creating under meaning $meaningId with payload: $antonymDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateAntonymEvent(meaningId, antonymDto, auditingInformation))
            creator.create(meaningId, mapper.antonymDtoToAntonym(antonymDto))
        }.id
    }

    @PutMapping(ANTONYM_PATH + ANTONYM_ID_PATH)
    fun update(@AntonymExists @PathVariable(ANTONYM_ID) antonymId: Long,
        @Validated(OnUpdate::class) @RequestBody antonymDto: AntonymDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Antonym $antonymId updating with payload: $antonymDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateAntonymEvent(antonymId, antonymDto, auditingInformation))
            updater.update(antonymId, mapper.antonymDtoToAntonym(antonymDto))
        }.id
    }

    @DeleteMapping(ANTONYM_PATH + ANTONYM_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@AntonymExists @PathVariable(ANTONYM_ID) antonymId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Antonym $antonymId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteAntonymEvent(antonymId, auditingInformation))
            remover.remove(antonymId)
        }
    }
}
