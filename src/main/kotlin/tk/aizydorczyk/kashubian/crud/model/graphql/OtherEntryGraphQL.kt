package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class OtherEntryGraphQL {
    @get:Key
    @get:Column("other_entry_id")
    var id: Long? = null

    @get:Column("other_entry_word")
    var word: String? = null
}