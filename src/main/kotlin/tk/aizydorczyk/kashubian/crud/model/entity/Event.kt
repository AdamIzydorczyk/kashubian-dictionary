package tk.aizydorczyk.kashubian.crud.model.entity

import com.fasterxml.jackson.databind.node.ObjectNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "event")
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_generator")
    @SequenceGenerator(name = "event_id_generator",
            sequenceName = "event_id_sequence",
            allocationSize = 1)
    val id: Long = 0L,
    val invokedBy: String,
    val invokedAt: LocalDateTime,
    val eventType: String,
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val event: ObjectNode?
)
