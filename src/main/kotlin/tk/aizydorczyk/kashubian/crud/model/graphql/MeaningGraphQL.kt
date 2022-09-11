package tk.aizydorczyk.kashubian.crud.model.graphql

import com.fasterxml.jackson.databind.JsonNode
import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class MeaningGraphQL {
    @get:Key
    @get:Column("meaning_id")
    var id: Long? = null

    @get:Column("meaning_definition")
    var definition: String? = null

    @get:Column("meaning_origin")
    var origin: String? = null

    @get:Column("hyperonyms")
    var hyperonyms: JsonNode? = null

    @get:Column("hyponyms")
    var hyponyms: JsonNode? = null

    var translation: TranslationGraphQL? = null

    var proverbs: List<ProverbGraphQL>? = emptyList()

    var quotes: List<QuoteGraphQL>? = emptyList()

    var examples: List<ExampleGraphQL>? = emptyList()

    var phrasalVerbs: List<PhrasalVerbGraphQL>? = emptyList()
}