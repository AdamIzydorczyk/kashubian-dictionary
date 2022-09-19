package tk.aizydorczyk.kashubian.crud.model.graphql

import com.fasterxml.jackson.databind.JsonNode


data class KashubianEntryGraphQL(
    val id: Long?,
    val word: String?,
    val normalizedWord: String?,
    val note: String?,
    val priority: Boolean?,
    val partOfSpeech: String?,
    val partOfSpeechSubType: String?,
    var base: KashubianEntrySimplifiedGraphQL? = null,
    val bases: JsonNode?,
    val variation: JsonNode?,
    val derivatives: JsonNode?,
    val meaningsCount: Long? = 0L,
    var soundFile: SoundFileGraphQL? = null,
    val others: MutableSet<OtherGraphQL> = mutableSetOf(),
    val meanings: MutableSet<MeaningGraphQL> = mutableSetOf()
)