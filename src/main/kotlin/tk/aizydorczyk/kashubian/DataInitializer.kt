package tk.aizydorczyk.kashubian

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
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
import tk.aizydorczyk.kashubian.crud.model.dto.ExampleDto
import tk.aizydorczyk.kashubian.crud.model.dto.IdiomDto
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.dto.QuoteDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.TranslationDto
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime.now
import java.util.Base64.getDecoder
import kotlin.math.min

@Component
@EnableConfigurationProperties(RandomDataInitializerProperties::class)
class DataInitializer(
    private val kashubianEntryController: KashubianEntryController,
    private val repository: KashubianEntryRepository,
    private val properties: RandomDataInitializerProperties,
    private val objectMapper: ObjectMapper
) : ApplicationRunner {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

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
        val seedWords = readSeedVocabulary().words.take(properties.size)

        val created = LinkedHashMap<String, CreatedEntry>()
        seedWords.forEach { seedWord ->
            kashubianEntryController.create(seedWord.toEntryDto(created),
                    AuditingInformation("seeded", now()))
                    .let { response ->
                        created[seedWord.word] = CreatedEntry(
                                entryId = response.entryId,
                                meaningId = response.meaningIds.first())
                        seedWord.sound?.let { uploadSeedSound(seedWord.word, it, response.entryId) }
                    }
            logger.info("Seeded entry '${seedWord.word}'")
        }
        logger.info("Seed entries generation finished (${min(seedWords.size, properties.size)} entries)")
    }

    /**
     * Sound files come from /seed/sounds and go through the ordinary upload
     * endpoint, so they are stored (and recorded as events) exactly like the
     * files an editor uploads.
     */
    private fun uploadSeedSound(word: String, sound: SeedSound, entryId: Long) {
        val fileName = sound.file.substringAfterLast('/')
        kashubianEntryController.uploadSoundFile(entryId,
                FakeMultipartFile(fileName, sound.type, readSeedResource(sound.file)),
                AuditingInformation("seeded", now()))
        logger.info("Seeded ${if (sound.generated) "generated mock sound" else "sound"} '$fileName' for '$word'")
    }

    private fun readSeedVocabulary(): SeedVocabulary =
        javaClass.getResourceAsStream(SEED_FILE).use {
            objectMapper.readValue(it, SeedVocabulary::class.java)
        }

    private fun readSeedResource(path: String): ByteArray =
        javaClass.getResourceAsStream("$SEED_DIR/$path")?.readBytes()
                ?: throw IllegalStateException("seed data error: '$path' is missing from the seed resources")

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

    companion object {
        private const val SEED_DIR = "/seed"
        private const val SEED_FILE = "$SEED_DIR/kashubian-words.json"
    }
}

/**
 * One Kashubian dictionary word with glosses, as stored in
 * /seed/kashubian-words.json (real vocabulary from published
 * Kashubian dictionaries and word lists, used only for seed data).
 *
 * Cross-entry links (synonyms, antonyms, hypernyms, related entries,
 * base form) reference other seed words by name; the seeder resolves
 * them to database ids, so a referenced word must appear earlier in
 * the file than the word referencing it.
 */
data class SeedVocabulary(
    val source: String? = null,
    val words: List<SeedWord> = emptyList()
)

data class SeedWord(
    val word: String,
    val partOfSpeech: PartOfSpeechType,
    val partOfSpeechSubType: PartOfSpeechSubType,
    val definition: String,
    val polish: String,
    val english: String,
    val note: String? = null,
    val origin: String? = null,
    // inflection paradigm as JSON; keys must match the *Variation model for
    // the partOfSpeechSubType, validated on create like any other entry
    val variation: ObjectNode? = null,
    // sound file played on the entry's page; path relative to /seed
    val sound: SeedSound? = null,
    val examples: List<SeedExample> = emptyList(),
    val proverbs: List<SeedProverb> = emptyList(),
    val idioms: List<SeedIdiom> = emptyList(),
    val quotes: List<SeedQuote> = emptyList(),
    val synonymsOf: List<String> = emptyList(),
    val antonymOf: String? = null,
    val hypernymOf: String? = null,
    val baseOf: String? = null,
    val relatedTo: List<SeedRelated> = emptyList()
) {
    fun toEntryDto(created: Map<String, CreatedEntry>): KashubianEntryDto = KashubianEntryDto(
            word = word,
            note = note,
            // core vocabulary; find_word_of_the_day only rotates over priority entries
            priority = true,
            partOfSpeech = partOfSpeech,
            partOfSpeechSubType = partOfSpeechSubType,
            variation = variation,
            base = baseOf?.let { requireCreated(created, it, "base of").entryId },
            meanings = listOf(MeaningDto(
                    translation = TranslationDto(
                            polish = polish,
                            english = english,
                            german = null,
                            ukrainian = null),
                    definition = definition,
                    origin = origin,
                    hyperonym = hypernymOf?.let { requireCreated(created, it, "hypernym of").meaningId },
                    proverbs = proverbs.map { ProverbDto(it.proverb, it.note ?: "") },
                    idioms = idioms.map { IdiomDto(it.idiom, it.note ?: "") },
                    quotes = quotes.map { QuoteDto(it.quote, it.note ?: "") },
                    examples = examples.map { ExampleDto(it.example, it.note) },
                    synonyms = synonymsOf.map {
                        SynonymDto(requireCreated(created, it, "synonym of").meaningId, null)
                    },
                    antonyms = antonymOf?.let {
                        listOf(AntonymDto(requireCreated(created, it, "antonym of").meaningId, null))
                    } ?: emptyList())),
            others = relatedTo.map { related ->
                OtherDto(requireCreated(created, related.word, "related to").entryId, related.note)
            }
    )

    private fun requireCreated(created: Map<String, CreatedEntry>, reference: String, context: String): CreatedEntry =
        checkNotNull(created[reference]) {
            "seed data error: '$reference' referenced as $context '$word' must be created earlier in the seed vocabulary file"
        }
}

data class CreatedEntry(val entryId: Long, val meaningId: Long)

data class SeedExample(val example: String, val note: String? = null)

data class SeedProverb(val proverb: String, val note: String? = null)

data class SeedIdiom(val idiom: String, val note: String? = null)

data class SeedQuote(val quote: String, val note: String? = null)

data class SeedRelated(val word: String, val note: String? = null)

/**
 * A sound file next to the seed vocabulary, stored as the entry's sound file.
 *
 * `file` is a path relative to /seed, `type` is the audio type the browser
 * receives, and `generated` marks files that are not real pronunciations but
 * tones produced by scripts/generate-mock-sounds.sh.
 */
data class SeedSound(val file: String, val type: String, val generated: Boolean = false)

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
    override fun getInputStream(): InputStream = ByteArrayInputStream(bytes)

    override fun getName(): String = name

    override fun getOriginalFilename(): String = name

    override fun getContentType(): String = contentType

    override fun isEmpty(): Boolean = bytes.isEmpty()

    override fun getSize(): Long = bytes.size.toLong()

    override fun getBytes(): ByteArray = bytes

    override fun transferTo(dest: File) = dest.writeBytes(bytes)
}
