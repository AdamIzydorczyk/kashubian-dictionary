package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.databind.node.ObjectNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "variation")
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class Variation(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variation_id_generator")
        @SequenceGenerator(name = "variation_id_generator", sequenceName = "variation_id_sequence", allocationSize = 1)
        val id: Long,
        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        var variation: ObjectNode,
)
