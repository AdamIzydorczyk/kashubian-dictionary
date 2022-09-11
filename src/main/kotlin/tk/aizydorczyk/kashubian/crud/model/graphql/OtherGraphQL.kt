package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class OtherGraphQL {
    @get:Key
    @get:Column("other_id")
    var id: Long? = null

    @get:Column("other_note")
    var note: String? = null
    var other: OtherEntryGraphQL? = null
}