package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import org.simpleflatmapper.map.annotation.Column

class OtherGraphQL {
    @get:Column("other_id")
    var id: Long? = null

    @get:Column("other_note")
    var note: String? = null
    var other: OtherEntryGraphQL? = null
}