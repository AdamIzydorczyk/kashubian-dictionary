package tk.aizydorczyk.kashubian.crud.domain

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
import tk.aizydorczyk.kashubian.crud.event.CreateEntryEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteEntryEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteSoundFileEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateEntryEvent
import tk.aizydorczyk.kashubian.crud.event.UploadSoundFileEvent
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.ResponseDto
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryMapper
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.FILE_PATH
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.KASHUBIAN_ENTRY_PATH
import tk.aizydorczyk.kashubian.crud.validator.AudioType
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.FileExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport
import java.util.Base64.getEncoder


@RestController
@RequestMapping(KASHUBIAN_ENTRY_PATH)
@Validated
@Tag(name = "Kashubian Entry")
class KashubianEntryController(
    val kashubianMapper: KashubianEntryMapper,
    val creator: KashubianEntryCreator,
    val updater: KashubianEntryUpdater,
    val remover: KashubianEntryRemover,
    val uploader: KashubianEntrySoundFileUploader,
    val downloader: KashubianEntrySoundFileDownloader,
    val fileRemover: KashubianEntrySoundFileRemover,
    val eventPublisher: ApplicationEventPublisher,
    val transactionSupport: TransactionSupport) {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated(OnCreate::class) @RequestBody entryDto: KashubianEntryDto,
        auditingInformation: AuditingInformation): ResponseDto {
        logger.info("Entry creating with payload: $entryDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(CreateEntryEvent(entryDto, auditingInformation))
            creator.create(kashubianMapper.toEntity(entryDto), auditingInformation)
        }.run { ResponseDto(this.id, this.meanings.map { it.id }) }
    }

    @PostMapping(FILE_PATH)
    @ResponseStatus(CREATED)
    fun uploadSoundFile(@EntryExists @PathVariable entryId: Long,
        @AudioType @RequestPart(required = true) soundFile: MultipartFile,
        auditingInformation: AuditingInformation) {
        logger.info("File uploading with name: ${soundFile.name}")
        transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UploadSoundFileEvent(
                    entryId,
                    soundFile.originalFilename.toString(),
                    soundFile.contentType.toString(),
                    getEncoder().encodeToString(soundFile.bytes),
                    auditingInformation))
            uploader.upload(entryId,
                    soundFile.originalFilename.toString(),
                    soundFile.contentType.toString(),
                    soundFile.bytes,
                    auditingInformation)
        }
    }

    @GetMapping(FILE_PATH)
    fun downloadSoundFile(@EntryExists @FileExists @PathVariable entryId: Long): ResponseEntity<ByteArray> {
        logger.info("File downloading by entry id: $entryId")
        return transactionSupport.executeInReadOnlyTransaction {
            downloader.download(entryId).let {
                ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, "attachment; filename=\"${it.fileName}\"")
                    .body(it.file)
            }
        }
    }

    @PutMapping(ENTRY_ID_PATH)
    fun update(@EntryExists @PathVariable entryId: Long,
        @Validated(OnUpdate::class) @RequestBody entryDto: KashubianEntryDto,
        auditingInformation: AuditingInformation): ResponseDto {
        logger.info("Entry id: $entryId updating with payload: $entryDto")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(UpdateEntryEvent(entryId, entryDto, auditingInformation))
            updater.update(entryId, kashubianMapper.toEntity(entryDto), auditingInformation)
        }.run { ResponseDto(this.id, this.meanings.map { it.id }) }
    }

    @DeleteMapping(ENTRY_ID_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@EntryExists @PathVariable entryId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("Entry id: $entryId deleting")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteEntryEvent(entryId, auditingInformation))
            remover.remove(entryId)
        }
    }

    @DeleteMapping(FILE_PATH)
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteFile(@EntryExists @FileExists @PathVariable entryId: Long,
        auditingInformation: AuditingInformation) {
        logger.info("File entry id: $entryId deleting")
        return transactionSupport.executeInTransaction {
            eventPublisher.publishEvent(DeleteSoundFileEvent(entryId, auditingInformation))
            fileRemover.remove(entryId)
        }
    }
}