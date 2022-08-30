package tk.aizydorczyk.kashubian.crud.model.entitysearch

import com.fasterxml.jackson.databind.node.ObjectNode

data class SearchKashubianEntry(
    val id: Long?,
    val word: String?,
    val normalizedWord: String?,
    val note: String?,
    val priority: Boolean?,
    val partOfSpeech: String?,
    val partOfSpeechSubType: String?,
    val variation: ObjectNode?,
    val soundFile: Set<SearchSoundFile>? = emptySet(),
    val meanings: Set<SearchMeaning>? = emptySet(),
    val meaningsCount: Long?,
    val others: Set<SearchOther>? = emptySet()
)