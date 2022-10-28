package tk.aizydorczyk.kashubian

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.javafaker.Faker
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryController
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.event.CreateEntryEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteEntryEvent
import tk.aizydorczyk.kashubian.crud.event.DeleteSoundFileEvent
import tk.aizydorczyk.kashubian.crud.event.UpdateEntryEvent
import tk.aizydorczyk.kashubian.crud.event.UploadSoundFileEvent
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.crud.query.rest.ExampleVariationsGenerator
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime.now
import java.util.Base64.getDecoder
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.random.Random
import kotlin.streams.toList

typealias JavaRandom = java.util.Random

@Component
@EnableConfigurationProperties(RandomDataInitializerProperties::class)
class DataInitializer(
    private val kashubianEntryController: KashubianEntryController,
    private val exampleVariationsGenerator: ExampleVariationsGenerator,
    private val repository: KashubianEntryRepository,
    private val properties: RandomDataInitializerProperties,
    private val objectMapper: ObjectMapper
) : ApplicationRunner {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    private final val random = JavaRandom()
    private final val faker = Faker(Locale(properties.language), random)

    override fun run(args: ApplicationArguments) {
        if (repository.countEvents() > 0 && repository.countAllEntries() <= 0) {
            logger.info("Data recreation from events started")
            recreateDataFromEvents()
        }


        if (repository.countAllEntries() > 0) {
            logger.info("Entries already exists in database")
            return
        }

        generateData()
    }

    private fun generateData() {
        val generatorCounter = AtomicLong(1L)
        val parameters =
            prepareGeneratorParameters(generatorCounter::get) {
                exampleVariationsGenerator.variationsExamples().random(Random(generatorCounter.get()))
            }

        val generator = EasyRandom(parameters)
        generator.objects(KashubianEntryDto::class.java, properties.size)
            .forEach {
                kashubianEntryController.create(it, AuditingInformation("generated", now()))
                    .let { response ->
                        kashubianEntryController.uploadSoundFile(response.entryId,
                                FakeMultipartFile(), AuditingInformation("generated",
                                now()))
                    }
                logger.info("Generated entry $generatorCounter from ${properties.size}")
                generatorCounter.incrementAndGet()
            }
        logger.info("Entries generation finished")
    }

    private fun recreateDataFromEvents() {
        repository.findAllEvents().forEach {
            if (it.eventType == "CREATE_ENTRY") {
                val event = objectMapper.treeToValue(it.event,
                        CreateEntryEvent::class.java)
                kashubianEntryController.create(event.entryDto,
                        event.auditingInformation)
            }
            if (it.eventType == "UPDATE_ENTRY") {
                val event = objectMapper.treeToValue(it.event,
                        UpdateEntryEvent::class.java)
                kashubianEntryController.update(event.entryId,
                        event.entryDto,
                        event.auditingInformation)
            }
            if (it.eventType == "DELETE_ENTRY") {
                val event = objectMapper.treeToValue(it.event,
                        DeleteEntryEvent::class.java)
                kashubianEntryController.delete(event.entryId,
                        event.auditingInformation)
            }
            if (it.eventType == "DELETE_SOUND_FILE") {
                val event = objectMapper.treeToValue(it.event,
                        DeleteSoundFileEvent::class.java)
                kashubianEntryController.deleteFile(event.entryId,
                        event.auditingInformation)
            }
            if (it.eventType == "UPLOAD_SOUND_FILE") {
                val event = objectMapper.treeToValue(it.event,
                        UploadSoundFileEvent::class.java)
                kashubianEntryController.uploadSoundFile(event.entryId,
                        FakeMultipartFile(event.originalFilename,
                                event.contentType,
                                getDecoder().decode(event.encodedFile)),
                        event.auditingInformation)
            }
        }
    }

    private fun prepareGeneratorParameters(
        generatorCounter: () -> Long,
        variationWithTypesFunction: () -> Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>): EasyRandomParameters {

        return with(EasyRandomParameters()) {
            collectionSizeRange(1, 3)

            randomize(FieldPredicates.named("variation")) {
                variationWithTypesFunction.invoke().first
            }

            randomize(PartOfSpeechType::class.java) {
                variationWithTypesFunction.invoke().third
            }

            randomize(PartOfSpeechSubType::class.java) {
                variationWithTypesFunction.invoke().second
            }

            randomize(FieldPredicates.ofType(String::class.java)) {
                selectWordFunction() + generatorCounter.invoke()
            }

            randomize(FieldPredicates.named("others")) {
                repository.findByTypeAndIds(KashubianEntry::class.java,
                        generateEntitiesAmount(generatorCounter.invoke()))
                    .map { OtherDto(it.id, selectWordFunction() + generatorCounter.invoke().toInt()) }
            }

            randomize(FieldPredicates.named("synonyms")) {
                repository.findByTypeAndIds(Meaning::class.java, generateEntitiesAmount(generatorCounter.invoke()))
                    .map { SynonymDto(it.id, selectWordFunction() + generatorCounter.invoke()) }
            }

            randomize(FieldPredicates.named("antonyms")) {
                repository.findByTypeAndIds(Meaning::class.java, generateEntitiesAmount(generatorCounter.invoke()))
                    .map { AntonymDto(it.id, selectWordFunction() + generatorCounter.invoke()) }
            }

            randomize(FieldPredicates.named("base")) {
                repository.findByTypeAndIds(KashubianEntry::class.java,
                        generateEntitiesAmount(generatorCounter.invoke()))
                    .firstOrNull()?.id
            }

            randomize(FieldPredicates.named("hyperonym")) {
                repository.findByTypeAndIds(Meaning::class.java, generateEntitiesAmount(generatorCounter.invoke()))
                    .firstOrNull()?.id
            }
        }
    }

    private fun generateEntitiesAmount(generatorCounter: Long): List<Long> =
        random.longs(1, -1, generatorCounter).toList()

    private fun selectWordFunction(): String =
        selectRandomWordFromSets().let { it.substring(0, min(it.length, 90)) }


    private fun selectRandomWordFromSets(): String = listOf(
            faker.address()::country,
            faker.address()::state,
            faker.address()::city,
            faker.name()::firstName,
            faker.name()::lastName,
            faker.name()::prefix,
            faker.lorem()::word,
            faker.lorem()::sentence)
        .random().invoke()
}

@ConstructorBinding
@ConfigurationProperties(prefix = "initializer")
data class RandomDataInitializerProperties(
    val size: Int,
    val language: String
)

data class FakeMultipartFile(
    private val name: String = "test.mp3",
    private val contentType: String = "audio/mp3",
    private val bytes: ByteArray = ByteArray(1)) : MultipartFile {
    override fun getInputStream(): InputStream = ByteArrayInputStream.nullInputStream()

    override fun getName(): String = name

    override fun getOriginalFilename(): String = name

    override fun getContentType(): String = contentType

    override fun isEmpty(): Boolean = false

    override fun getSize(): Long = 1L

    override fun getBytes(): ByteArray = bytes

    override fun transferTo(dest: File) {}
}
