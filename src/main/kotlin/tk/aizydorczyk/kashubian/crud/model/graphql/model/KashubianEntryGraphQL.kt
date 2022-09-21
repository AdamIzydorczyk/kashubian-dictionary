package tk.aizydorczyk.kashubian.crud.model.graphql.model

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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KashubianEntryGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}