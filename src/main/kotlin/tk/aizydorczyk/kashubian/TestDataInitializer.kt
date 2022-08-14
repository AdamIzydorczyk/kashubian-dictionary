package tk.aizydorczyk.kashubian

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.javafaker.Faker
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.web.multipart.MultipartFile
import tk.aizydorczyk.kashubian.domain.KashubianEntryController
import tk.aizydorczyk.kashubian.domain.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.dto.OtherDto
import tk.aizydorczyk.kashubian.domain.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Meaning
import tk.aizydorczyk.kashubian.domain.model.json.VerbVariation
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.util.Locale
import java.util.Random
import javax.persistence.EntityManager
import kotlin.math.min
import kotlin.streams.toList


//@Component
class TestDataInitializer(
    val kashubianEntryController: KashubianEntryController,
    val objectMapper: ObjectMapper,
    @Qualifier("defaultEntityManager") val entityManager: EntityManager) : ApplicationRunner {

    val logger = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val variationsGenerator = EasyRandom()
        val variations = listOf(
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.NEUTER) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.MASCULINE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.FEMININE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.PLURAL_MASCULINE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.NON_MASCULINE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.CONJUGATION_I) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.CONJUGATION_II) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.CONJUGATION_III) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.CONJUGATION_IV) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.INFLECTIV_ADJECTIVE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.UNINFLECTIV_ADJECTIVE) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.INFLECTIV_NUMERAL) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.UNINFLECTIV_NUMERAL) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.NOUN_PRONOUN) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.ADJECTIVE_PRONOUN) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.NUMERAL_PRONOUN) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.ADVERB_PRONOUN) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.ADVERB) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.PREPOSITION) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.CONJUNCTION) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.INTERJECTION) },
                variationsGenerator.nextObject(VerbVariation::class.java)
                    .let { objectMapper.convertValue(it, ObjectNode::class.java) }
                    .let { Pair(it, PartOfSpeechSubType.PARTICIPLE) },
        ).toTypedArray()


        var index = 0L
        val random = Random()


        val fakerEn = Faker()
        val fakerPl = Faker(Locale("pl-PL"))
        val fakerDe = Faker(Locale("de_DE"))
        val fakerUa = Faker(Locale("uk_UA"))

        val parameters = EasyRandomParameters()
        parameters.stringLengthRange(64, 64)
        parameters.collectionSizeRange(3, 3)
        parameters.randomize(ObjectNode::class.java) { variations[Math.floorMod(21, index + 1).toInt()].first }
        parameters.randomize(PartOfSpeechType::class.java) {
            variations[Math.floorMod(21, index + 1).toInt()].second.partOfSpeechType
        }
        parameters.randomize(PartOfSpeechSubType::class.java) {
            variations[Math.floorMod(21, index + 1).toInt()].second
        }

        parameters.randomize(FieldPredicates.named("word")) {
            selectWord(fakerPl).let {
                it[random.nextInt(it.size - 1)].invoke()
                    .let { value -> value.substring(0, min(value.length, 250)) } + index
            }
        }

        parameters.randomize(FieldPredicates.named("polish")) {
            selectWord(fakerPl).let {
                it[random.nextInt(it.size - 1)].invoke()
                    .let { value -> value.substring(0, min(value.length, 250)) } + index
            }
        }

        parameters.randomize(FieldPredicates.named("english")) {
            selectWord(fakerEn).let {
                it[random.nextInt(it.size - 1)].invoke()
                    .let { value -> value.substring(0, min(value.length, 250)) } + index
            }
        }

        parameters.randomize(FieldPredicates.named("german")) {
            selectWord(fakerDe).let {
                it[random.nextInt(it.size - 1)].invoke()
                    .let { value -> value.substring(0, min(value.length, 250)) } + index
            }
        }

        parameters.randomize(FieldPredicates.named("ukrainian")) {
            selectWord(fakerUa).let {
                it[random.nextInt(it.size - 1)].invoke()
                    .let { value -> value.substring(0, min(value.length, 250)) } + index
            }
        }

        parameters.randomize(FieldPredicates.named("others")) {
            entityManager.createQuery("select e from KashubianEntry e where e.id in (:ids)",
                    KashubianEntry::class.java).setParameter("ids", random.longs(1, -1, index)
                .toList()).resultList.map { OtherDto(it.id, "test") }
        }
        parameters.randomize(FieldPredicates.named("synonyms")) {
            entityManager.createQuery("select m from Meaning m where m.id in (:ids)",
                    Meaning::class.java).setParameter("ids", random.longs(3, -1, index * 3)
                .toList()).resultList.map { SynonymDto(it.id, "test") }
        }
        parameters.randomize(FieldPredicates.named("antonyms")) {
            entityManager.createQuery("select m from Meaning m where m.id in (:ids)",
                    Meaning::class.java).setParameter("ids", random.longs(3, -1, index * 3)
                .toList()).resultList.map { AntonymDto(it.id, "test") }
        }
        parameters.randomize(FieldPredicates.named("base")) {
            entityManager.createQuery("select m from Meaning m where m.id in (:ids)",
                    Meaning::class.java).setParameter("ids", random.longs(1, -1, index * 3)
                .toList()).resultList.firstOrNull()?.id
        }
        parameters.randomize(FieldPredicates.named("superordinate")) {
            entityManager.createQuery("select m from Meaning m where m.id in (:ids)",
                    Meaning::class.java).setParameter("ids", random.longs(1, -1, index * 3)
                .toList()).resultList.firstOrNull()?.id
        }

        val generator = EasyRandom(parameters)
        generator.objects(KashubianEntryDto::class.java, 1000)
            .forEach {
                kashubianEntryController.create(it)
                    .let { response -> kashubianEntryController.uploadSoundFile(response.entryId, FakeMultipartFile()) }
                index++
                logger.info(index.toString())
            }
    }

    private fun selectWord(faker: Faker): List<() -> String> = listOf(
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
    )

    class FakeMultipartFile : MultipartFile {
        override fun getInputStream(): InputStream = ByteArrayInputStream.nullInputStream()

        override fun getName(): String = "test"

        override fun getOriginalFilename(): String = "test"

        override fun getContentType(): String = "test"

        override fun isEmpty(): Boolean = false

        override fun getSize(): Long = 1L

        override fun getBytes(): ByteArray = ByteArray(1)

        override fun transferTo(dest: File) {}

    }
}