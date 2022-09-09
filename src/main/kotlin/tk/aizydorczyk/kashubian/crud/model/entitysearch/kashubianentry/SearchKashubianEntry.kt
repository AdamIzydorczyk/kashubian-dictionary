package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import tk.aizydorczyk.kashubian.crud.model.entitysearch.SearchMeaning
import tk.aizydorczyk.kashubian.crud.model.entitysearch.SearchOther
import tk.aizydorczyk.kashubian.crud.model.entitysearch.SearchSoundFile

data class SearchKashubianEntry(
    val denseRank: Long,
    val id: Long?,
    val word: String?,
    val normalizedWord: String?,
    val note: String?,
    val priority: Boolean?,
    val partOfSpeech: String?,
    val partOfSpeechSubType: String?,
    val variation: ObjectNode?,
    val bases: ArrayNode?,
    val derivatives: ArrayNode?,
    val soundFile: Set<SearchSoundFile>? = emptySet(),
    val meanings: Set<SearchMeaning>? = emptySet(),
    val meaningsCount: Long?,
    val others: Set<SearchOther>? = emptySet()
)