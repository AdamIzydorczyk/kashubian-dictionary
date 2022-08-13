package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.databind.node.ObjectNode
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "variation")
data class Variation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Lob
        var variation: ObjectNode?,
)
