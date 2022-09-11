package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class ProverbGraphQL {
    @get:Key
    @get:Column("proverb_id")
    var id: Long? = null

    @get:Column("proverb_note")
    var note: String? = null

    @get:Column("proverb_proverb")
    var proverb: String? = null
}

