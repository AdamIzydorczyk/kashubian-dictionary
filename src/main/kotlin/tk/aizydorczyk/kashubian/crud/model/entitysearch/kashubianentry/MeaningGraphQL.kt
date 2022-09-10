package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import org.simpleflatmapper.map.annotation.Column

class MeaningGraphQL {
    @get:Column("meaning_id")
    var id: Long? = null

    @get:Column("meaning_definition")
    var definition: String? = null
}