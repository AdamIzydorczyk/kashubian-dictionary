package tk.aizydorczyk.kashubian

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
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.VariationDto
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.crud.query.ExampleVariationsGenerator
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.random.Random
import kotlin.streams.toList

typealias JavaRandom = java.util.Random

@Component
@EnableConfigurationProperties(RandomDataInitializerProperties::class)
class RandomDataInitializer(
    val kashubianEntryController: KashubianEntryController,
    val exampleVariationsGenerator: ExampleVariationsGenerator,
    val repository: KashubianEntryRepository,
    private val properties: RandomDataInitializerProperties) : ApplicationRunner {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    private final val random = JavaRandom()
    private final val faker = Faker(Locale(properties.language), random)

    override fun run(args: ApplicationArguments?) {
        if (repository.countAllEntries() > 0
        ) {
            return
        }

        val generatorCounter = AtomicLong(1L)
        val parameters =
            prepareGeneratorParameters(generatorCounter::get) {
                exampleVariationsGenerator.variationsExamples().random(Random(generatorCounter.get()))
            }

        val generator = EasyRandom(parameters)
        generator.objects(KashubianEntryDto::class.java, properties.size)
            .forEach {
                kashubianEntryController.create(it)
                    .let { response -> kashubianEntryController.uploadSoundFile(response.entryId, FakeMultipartFile()) }
                logger.info("Generated entry $generatorCounter from ${properties.size}")
                generatorCounter.incrementAndGet()
            }
        logger.info("Entries generation finished")
    }

    private fun prepareGeneratorParameters(
        generatorCounter: () -> Long,
        variationWithTypesFunction: () -> Triple<ObjectNode?, PartOfSpeechSubType, PartOfSpeechType>): EasyRandomParameters {

        return with(EasyRandomParameters()) {
            collectionSizeRange(0, 3)

            randomize(FieldPredicates.named("variation")) {
                variationWithTypesFunction.invoke().first?.let { VariationDto(it) }
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
                repository.findByTypeAndIds(Meaning::class.java, generateEntitiesAmount(generatorCounter.invoke()))
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

class FakeMultipartFile : MultipartFile {
    override fun getInputStream(): InputStream = ByteArrayInputStream.nullInputStream()

    override fun getName(): String = "test"

    override fun getOriginalFilename(): String = "test.mp3"

    override fun getContentType(): String = "audio/mp3"

    override fun isEmpty(): Boolean = false

    override fun getSize(): Long = 1L

    override fun getBytes(): ByteArray = ByteArray(1)

    override fun transferTo(dest: File) {}

}
