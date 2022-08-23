package tk.aizydorczyk.kashubian.crud.query

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.domain.WordOfTheDayProjection
import java.time.Clock.systemUTC
import java.time.LocalDate.now
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

@Component
class WordOfTheDayFinder(val kashubianEntryRepository: KashubianEntryRepository) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    val cache = AtomicReference(CachedWordOfTheDay(0L, WordOfTheDay(-1L, "", emptyList())))

    fun find(): WordOfTheDay {
        val currentDay = now(systemUTC())
        val seed = currentDay.toEpochDay()

        if (cache.seed() == seed) {
            return cache.wordOfTheDay()
        }

        val wordOfTheDay = kashubianEntryRepository.findAllPrioritizedEntryIds().let(::groupDefinitions)
            .ifEmpty { listOf(WordOfTheDay(-1L, "", emptyList())) }
            .random(Random(seed))

        cache.getAndSet(CachedWordOfTheDay(seed = seed, wordOfTheDay = wordOfTheDay))
        logger.info("Word of day for $currentDay selected and cached")
        return wordOfTheDay
    }

    private fun groupDefinitions(wordOfTheDayProjections: List<WordOfTheDayProjection>) =
        wordOfTheDayProjections.groupBy { Pair(it.id, it.word) }.map {
            WordOfTheDay(
                    entryId = it.key.first,
                    word = it.key.second,
                    definitions = it.value.map(WordOfTheDayProjection::definition))
        }


    data class CachedWordOfTheDay(val seed: Long, val wordOfTheDay: WordOfTheDay)
}

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.seed() = this.get().seed

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.wordOfTheDay() = this.get().wordOfTheDay

data class WordOfTheDay(val entryId: Long, val word: String, val definitions: List<String>)