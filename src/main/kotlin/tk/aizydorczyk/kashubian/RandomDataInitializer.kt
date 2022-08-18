package tk.aizydorczyk.kashubian

import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.javafaker.Faker
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.http.MediaType
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
import java.util.Random
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.streams.toList


@Component
class RandomDataInitializer(
    val kashubianEntryController: KashubianEntryController,
    val exampleVariationsGenerator: ExampleVariationsGenerator,
    val repository: KashubianEntryRepository,
    @Value("\${test.data.initializer.generated.elements.size}") val generatedSize: Int) : ApplicationRunner {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    private final val random = Random()
    private final val faker = Faker(random)

    override fun run(args: ApplicationArguments?) {
        if (repository.countAllEntries() > 0
        ) {
            return
        }

        val generatorCounter = AtomicLong(1L)
        val parameters =
            prepareGeneratorParameters(generatorCounter::get) {
                exampleVariationsGenerator.variationsExamples().random(kotlin.random.Random(generatorCounter.get()))
            }

        val generator = EasyRandom(parameters)
        generator.objects(KashubianEntryDto::class.java, generatedSize)
            .forEach {
                kashubianEntryController.create(it)
                    .let { response -> kashubianEntryController.uploadSoundFile(response.entryId, FakeMultipartFile()) }
                logger.info("Generated entry $generatorCounter from $generatedSize")
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
                    .map { OtherDto(it.id, selectWordFunction() + generatorCounter.invoke()) }
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
            faker.ancient()::god,
            faker.ancient()::hero,
            faker.ancient()::titan,
            faker.ancient()::primordial,
            faker.book()::title,
            faker.animal()::name,
            faker.name()::firstName,
            faker.app()::name,
            faker.aquaTeenHungerForce()::character,
            faker.artist()::name,
            faker.avatar()::image,
            faker.aviation()::aircraft,
            faker.backToTheFuture()::quote,
            faker.backToTheFuture()::character,
            faker.beer()::hop,
            faker.beer()::malt,
            faker.beer()::name,
            faker.beer()::style,
            faker.beer()::yeast,
            faker.buffy()::bigBads,
            faker.buffy()::quotes,
            faker.buffy()::celebrities,
            faker.buffy()::characters,
            faker.business()::creditCardType,
            faker.cat()::breed,
            faker.cat()::name,
            faker.cat()::registry,
            faker.chuckNorris()::fact,
            faker.color()::name,
            faker.commerce()::department,
            faker.commerce()::material,
            faker.commerce()::productName,
            faker.country()::capital,
            faker.country()::name,
            faker.demographic()::demonym,
            faker.demographic()::sex,
            faker.demographic()::race,
            faker.demographic()::educationalAttainment,
            faker.demographic()::maritalStatus,
            faker.dog()::breed,
            faker.dog()::gender,
            faker.dog()::memePhrase,
            faker.job()::position,
            faker.job()::title
    ).random().invoke()
}

class FakeMultipartFile : MultipartFile {
    override fun getInputStream(): InputStream = ByteArrayInputStream.nullInputStream()

    override fun getName(): String = "test"

    override fun getOriginalFilename(): String = "test.txt"

    override fun getContentType(): String = MediaType.TEXT_PLAIN_VALUE

    override fun isEmpty(): Boolean = false

    override fun getSize(): Long = 1L

    override fun getBytes(): ByteArray = ByteArray(1)

    override fun transferTo(dest: File) {}

}
