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
import tk.aizydorczyk.kashubian.crud.event.CreateExampleEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteExampleEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateExampleEvent
import tk.aizydorczyk.kashubian.crud.model.dto.ExampleDto
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.EXAMPLE_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.EXAMPLE_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.EXAMPLE_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_PATH
import tk.aizydorczyk.kashubian.crud.validator.ExampleExists
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport

@RestController
@Validated
@Tag(name = "Example")
class ExampleController(
    private val mapper: MeaningMapper,
    private val creator: ExampleCreator,
    private val updater: ExampleUpdater,
    private val remover: ExampleRemover,
    private val eventPublisher: ApplicationEventPublisher,
    private val transactionSupport: TransactionSupport) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping(MEANING_PATH + MEANING_ID_PATH + EXAMPLE_PATH)
    @ResponseStatus(CREATED)
    fun create(@MeaningExists @PathVariable(MEANING_ID) meaningId: Long,
        @Validated(OnCreate::class) @RequestBody exampleDto: ExampleDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Example creating under meaning $meaningId with payload: $exampleDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateExampleEvent(meaningId, exampleDto, auditingInformation))
            creator.create(meaningId, mapper.toEntity(exampleDto))
        }.id
    }

    @PutMapping(EXAMPLE_PATH + EXAMPLE_ID_PATH)
    fun update(@ExampleExists @PathVariable(EXAMPLE_ID) exampleId: Long,
        @Validated(OnCreate::class) @RequestBody exampleDto: ExampleDto,
        auditingInformation: AuditingInformation): Long {
        logger.info("Example $exampleId updating with payload: $exampleDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateExampleEvent(exampleId, exampleDto, auditingInformation))
            updater.update(exampleId, mapper.toEntity(exampleDto))
        }.id
    }

    @DeleteMapping(EXAMPLE_PATH + EXAMPLE_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@ExampleExists @PathVariable(EXAMPLE_ID) exampleId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Example $exampleId deleting")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteExampleEvent(exampleId, auditingInformation))
            remover.remove(exampleId)
        }
    }
}
