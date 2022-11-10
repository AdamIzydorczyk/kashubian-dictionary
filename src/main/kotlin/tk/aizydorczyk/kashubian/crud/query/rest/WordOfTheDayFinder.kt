package tk.aizydorczyk.kashubian.crud.query.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.domain.WordOfTheDayProjection
import java.time.Clock
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Supplier

@Component
class WordOfTheDayFinder(
    private val kashubianEntryRepository: KashubianEntryRepository,
    clockProvider: Supplier<Clock>) {

    val clock = clockProvider.get()

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)
    val cache = AtomicReference(CachedWordOfTheDay(0L, WordOfTheDay(-1L, "", emptyList())))

    @Transactional(readOnly = true)
    fun find(): WordOfTheDay {
        val currentDay = now(clock)

        val seed = prepareSeed(currentDay)

        if (cache.seed() == seed) {
            return cache.wordOfTheDay()
        }

        val wordOfTheDay = kashubianEntryRepository.findRandomWordOfTheDay("0.$seed".toDouble())
            .let(::groupDefinitions)

        cache.getAndSet(CachedWordOfTheDay(seed = seed, wordOfTheDay = wordOfTheDay))
        logger.info("Word of day for $currentDay selected and cached")
        return wordOfTheDay
    }

    private fun prepareSeed(currentDay: LocalDateTime): Long = if (currentDay.hour < 12) {
        currentDay.toLocalDate().toEpochDay() * 2
    } else {
        currentDay.toLocalDate().toEpochDay() * 3
    }

    private fun groupDefinitions(wordOfTheDayProjections: List<WordOfTheDayProjection>) =
        wordOfTheDayProjections.groupBy { Pair(it.entryId, it.word) }.map {
            WordOfTheDay(
                    entryId = it.key.first,
                    word = it.key.second,
                    definitions = it.value.map(WordOfTheDayProjection::definition))
        }.ifEmpty { listOf(WordOfTheDay(-1L, "", emptyList())) }.first()


    data class CachedWordOfTheDay(val seed: Long, val wordOfTheDay: WordOfTheDay)
}

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.seed() = this.get().seed

fun AtomicReference<WordOfTheDayFinder.CachedWordOfTheDay>.wordOfTheDay() = this.get().wordOfTheDay

data class WordOfTheDay(val entryId: Long, val word: String, val definitions: List<String>)