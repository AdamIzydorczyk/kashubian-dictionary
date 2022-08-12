package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "variation")
data class Variation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(columnDefinition = "json")
        var variation: ObjectNode?,
)
