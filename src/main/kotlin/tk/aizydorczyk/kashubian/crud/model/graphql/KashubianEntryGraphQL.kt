package tk.aizydorczyk.kashubian.crud.model.graphql

import com.fasterxml.jackson.databind.JsonNode
import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class KashubianEntryGraphQL {
    @get:Key
    @get:Column("entry_id")
    var id: Long? = null

    @get:Column("entry_word")
    var word: String? = null

    @get:Column("entry_normalized_word")
    var normalizedWord: String? = null

    @get:Column("entry_note")
    var note: String? = null

    @get:Column("entry_priority")
    var priority: Boolean? = null

    @get:Column("entry_part_of_speech")
    var partOfSpeech: String? = null

    @get:Column("entry_part_of_speech_sub_type")
    var partOfSpeechSubType: String? = null

    @get:Column("bases")
    var bases: JsonNode? = null

    @get:Column("variation")
    var variation: JsonNode? = null

    @get:Column("derivatives")
    var derivatives: JsonNode? = null

    @get:Column("meanings_count")
    var meaningsCount: Long? = null
    var others: List<OtherGraphQL>? = emptyList()
    var meanings: List<MeaningGraphQL>? = emptyList()

    @get:Column("dense_rank")
    var denseRank: Long? = null
}