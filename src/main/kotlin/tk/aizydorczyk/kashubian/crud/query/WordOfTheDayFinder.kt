package tk.aizydorczyk.kashubian.crud.query

import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import java.time.Clock.systemUTC
import java.time.LocalDate.now
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

@Component
class WordOfTheDayFinder(val kashubianEntryRepository: KashubianEntryRepository) {

    val cache = AtomicReference(CachedWordOfTheDay(0L, 0L))

    fun find(): Long {
        val seed = now(systemUTC()).toEpochDay()

        if (cache.get().seed == seed) {
            return cache.get().wordOfTheDayId
        }

        val wordOfTheDayId = kashubianEntryRepository.findAllPrioritizedEntryIds().ifEmpty { listOf(0L) }
            .random(Random(seed))

        cache.getAndSet(CachedWordOfTheDay(seed = seed, wordOfTheDayId = wordOfTheDayId))

        return wordOfTheDayId
    }

    data class CachedWordOfTheDay(val seed: Long, val wordOfTheDayId: Long)
}