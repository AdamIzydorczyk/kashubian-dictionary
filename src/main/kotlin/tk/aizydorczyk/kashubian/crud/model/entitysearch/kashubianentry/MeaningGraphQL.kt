package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import com.fasterxml.jackson.databind.JsonNode
import org.simpleflatmapper.map.annotation.Column

class MeaningGraphQL {
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
}