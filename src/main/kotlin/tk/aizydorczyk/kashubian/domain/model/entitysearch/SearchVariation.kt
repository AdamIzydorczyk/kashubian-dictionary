package tk.aizydorczyk.kashubian.domain.model.entitysearch

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import javax.persistence.*

@Entity
@Table(name = "variation")
data class SearchVariation(
        @Id
    val id: Long,
        @Column(columnDefinition = "json")
        var variation: ObjectNode?,

        @OneToOne(mappedBy = "variation")
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
