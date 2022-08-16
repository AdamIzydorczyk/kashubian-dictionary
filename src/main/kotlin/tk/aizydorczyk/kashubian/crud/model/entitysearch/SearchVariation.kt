package tk.aizydorczyk.kashubian.crud.model.entitysearch

import com.fasterxml.jackson.databind.node.ObjectNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "variation")
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class SearchVariation(
    @Id
    val id: Long,
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var variation: ObjectNode,
    @ManyToOne
    @JoinColumn(name = "kashubian_entry_id")
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchVariation

        if (id != other.id) return false

        return true
    }
}
