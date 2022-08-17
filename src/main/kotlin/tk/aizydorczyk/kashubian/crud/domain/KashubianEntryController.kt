package tk.aizydorczyk.kashubian.crud.domain

import io.swagger.annotations.Api
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
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
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.ResponseDto
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryMapper
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate


@RestController
@RequestMapping("kashubian-entry")
@Api("Kashubian Entry", tags = ["KashubianEntry"])
class KashubianEntryController(
    val kashubianMapper: KashubianEntryMapper,
    val creator: KashubianEntryCreator,
    val updater: KashubianEntryUpdater,
    val remover: KashubianEntryRemover,
    val uploader: KashubianEntrySoundFileUploader,
    val downloader: KashubianEntrySoundFileDownloader) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated(OnCreate::class) @RequestBody entry: KashubianEntryDto) =
        creator.create(kashubianMapper.toEntity(entry))
            .run { ResponseDto(this.id, this.meanings.map { it.id }) }

    @PostMapping("/{entryId}/file")
    @ResponseStatus(CREATED)
    fun uploadSoundFile(@PathVariable entryId: Long,
        @RequestPart(required = true) soundFile: MultipartFile) {
        uploader.upload(entryId, soundFile)
    }

    @GetMapping("/{entryId}/file")
    fun downloadSoundFile(@PathVariable entryId: Long): ResponseEntity<ByteArray> =
        downloader.download(entryId).let {
            ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"${it.fileName}\"")
                .body(it.file)
        }

    @PutMapping("/{entryId}")
    fun update(
        @PathVariable entryId: Long,
        @Validated(OnUpdate::class) @RequestBody entry: KashubianEntryDto) =
        updater.update(entryId, kashubianMapper.toEntity(entry))
            .run { ResponseDto(this.id, this.meanings.map { it.id }) }

    @DeleteMapping("/{entryId}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable entryId: Long) {
        remover.remove(entryId)
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
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