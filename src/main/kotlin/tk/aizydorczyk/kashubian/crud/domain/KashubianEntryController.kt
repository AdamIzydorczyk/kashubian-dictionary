package tk.aizydorczyk.kashubian.crud.domain

import io.swagger.annotations.Api
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
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
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate


@RestController
@RequestMapping("kashubian-entry")
@Validated
@Api("Kashubian Entry", tags = ["KashubianEntry"])
class KashubianEntryController(
    val kashubianMapper: KashubianEntryMapper,
    val creator: KashubianEntryCreator,
    val updater: KashubianEntryUpdater,
    val remover: KashubianEntryRemover,
    val uploader: KashubianEntrySoundFileUploader,
    val downloader: KashubianEntrySoundFileDownloader,
    val fileRemover: KashubianEntrySoundFileRemover) {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated(OnCreate::class) @RequestBody entry: KashubianEntryDto): ResponseDto {
        logger.info("Entry creating with payload: $entry")
        return creator.create(kashubianMapper.toEntity(entry))
            .run { ResponseDto(this.id, this.meanings.map { it.id }) }
    }

    @PostMapping("/{entryId}/file")
    @ResponseStatus(CREATED)
    fun uploadSoundFile(@EntryExists @PathVariable entryId: Long,
        @RequestPart(required = true) soundFile: MultipartFile) {
        logger.info("File uploading with name: ${soundFile.name}")
        uploader.upload(entryId, soundFile)
    }

    @GetMapping("/{entryId}/file")
    fun downloadSoundFile(@EntryExists @PathVariable entryId: Long): ResponseEntity<ByteArray> {
        logger.info("File downloading by entry id: $entryId")
        return downloader.download(entryId).let {
            ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"${it.fileName}\"")
                .body(it.file)
        }
    }

    @PutMapping("/{entryId}")
    fun update(
        @EntryExists @PathVariable entryId: Long,
        @Validated(OnUpdate::class) @RequestBody entry: KashubianEntryDto): ResponseDto {
        logger.info("Entry id: $entryId updating with payload: $entry")
        return updater.update(entryId, kashubianMapper.toEntity(entry))
            .run { ResponseDto(this.id, this.meanings.map { it.id }) }
    }

    @DeleteMapping("/{entryId}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@EntryExists @PathVariable entryId: Long) {
        logger.info("Entry id: $entryId deleting")
        remover.remove(entryId)
    }

    @DeleteMapping("/{entryId}/file")
    @ResponseStatus(NO_CONTENT)
    fun deleteFile(@EntryExists @PathVariable entryId: Long) {
        logger.info("File entry id: $entryId deleting")
        fileRemover.remove(entryId)
    }
}