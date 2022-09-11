package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class PhrasalVerbGraphQL {
    @get:Key
    @get:Column("phrasal_verb_id")
    var id: Long? = null

    @get:Column("phrasal_verb_note")
    var note: String? = null

    @get:Column("phrasal_verb_phrasal_verb")
    var phrasalVerb: String? = null
}