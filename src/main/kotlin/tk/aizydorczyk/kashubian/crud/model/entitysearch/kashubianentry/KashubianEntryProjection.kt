package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

data class KashubianEntryProjection(
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
    val meaningsCount: Long?,
    val otherId: Long?,
    val otherNote: String?,
    var otherEntryId: Long?,
    var otherEntryWord: String?,
)