package tk.aizydorczyk.kashubian.crud.query

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import java.time.Clock.systemUTC
import java.time.LocalDate.now
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

@Component
class WordOfTheDayFinder(val kashubianEntryRepository: KashubianEntryRepository) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)
    val cache = AtomicReference(CachedWordOfTheDay(0L, 0L))

    fun find(): Long {
        val currentDay = now(systemUTC())
        val seed = currentDay.toEpochDay()

        if (cache.seed() == seed) {
            return cache.wordOfTheDayId()
        }

        val wordOfTheDayId = kashubianEntryRepository.findAllPrioritizedEntryIds().ifEmpty { listOf(0L) }
            .random(Random(seed))

        cache.getAndSet(CachedWordOfTheDay(seed = seed, wordOfTheDayId = wordOfTheDayId))
        logger.info("Word of day for $currentDay selected and cached")
        return wordOfTheDayId
    }

    data class CachedWordOfTheDay(val seed: Long, val wordOfTheDayId: Long)
}

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.seed() = this.get().seed

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.wordOfTheDayId() = this.get().wordOfTheDayId