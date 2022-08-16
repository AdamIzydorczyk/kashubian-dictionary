package tk.aizydorczyk.kashubian.crud.model.entity

import com.fasterxml.jackson.databind.node.ObjectNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "variation")
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class Variation(
    @Id
    var id: Long,
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var variation: ObjectNode,
    @Column(name = "kashubian_entry_id")
    val kashubianEntry: Long
)
