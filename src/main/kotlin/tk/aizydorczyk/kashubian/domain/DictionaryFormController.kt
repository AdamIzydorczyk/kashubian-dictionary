package tk.aizydorczyk.kashubian.domain

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.mapper.KashubianEntryMapper
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate


@RestController
@RequestMapping("kashubian-entry")
@Api("Kashubian Entry", tags = ["KashubianEntry"])
class KashubianEntryController(
    val kashubianMapper: KashubianEntryMapper,
    val creator: KashubianEntryCreator,
    val updater: KashubianEntryUpdater,
    val remover: KashubianEntryRemover,
    val finder: KashubianEntryFinder) {

    @GetMapping
    fun findAll() = finder.findAll()

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated(OnCreate::class) @RequestBody entry: KashubianEntryDto) =
        creator.create(kashubianMapper.toEntity(entry))

    @PutMapping("/{entryId}")
    fun update(
        @PathVariable entryId: Long,
        @Validated(OnUpdate::class) @RequestBody entry: KashubianEntryDto) =
        updater.update(entryId, kashubianMapper.toEntity(entry))

    @DeleteMapping("/{entryId}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable entryId: Long) {
        remover.remove(entryId)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException): Map<String, List<Any>> {
        val groupedErrors = ex.bindingResult.allErrors
            .groupBy { it.javaClass.simpleName }
        val objectErrors = groupedErrors["ViolationObjectError"].orEmpty()
            .map { it as ObjectError }.map { it.defaultMessage.toString() }
        val messages = groupedErrors["ViolationFieldError"].orEmpty()
            .map { it as FieldError }.map { mapOf("message" to it.defaultMessage, "fieldName" to it.field) }
        return mapOf("objectErrors" to objectErrors, "fieldErrors" to messages)
    }
}