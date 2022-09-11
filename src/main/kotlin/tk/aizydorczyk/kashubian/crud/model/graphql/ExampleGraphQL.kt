package tk.aizydorczyk.kashubian.crud.model.graphql

import org.simpleflatmapper.map.annotation.Column
import org.simpleflatmapper.map.annotation.Key

class ExampleGraphQL {
    @get:Key
    @get:Column("example_id")
    var id: Long? = null

    @get:Column("example_note")
    var note: String? = null

    @get:Column("example_example")
    var example: String? = null
}