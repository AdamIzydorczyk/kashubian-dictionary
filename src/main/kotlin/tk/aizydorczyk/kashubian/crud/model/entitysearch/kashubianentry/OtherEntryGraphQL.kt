package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import org.simpleflatmapper.map.annotation.Column

class OtherEntryGraphQL {
    @get:Column("other_entry_id")
    var id: Long? = null

    @get:Column("other_entry_word")
    var word: String? = null
}