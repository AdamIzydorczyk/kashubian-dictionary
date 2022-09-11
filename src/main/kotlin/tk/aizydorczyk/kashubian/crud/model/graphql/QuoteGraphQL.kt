package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class QuoteGraphQL {
    @get:Key
    @get:Column("quote_id")
    var id: Long? = null

    @get:Column("quote_note")
    var note: String? = null

    @get:Column("quote_quote")
    var quote: String? = null
}