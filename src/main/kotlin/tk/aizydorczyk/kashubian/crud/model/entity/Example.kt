package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "example")
data class Example(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "example_id_generator")
    @SequenceGenerator(name = "example_id_generator", sequenceName = "example_id_sequence", allocationSize = 1)
    var id: Long,
    val example: String?,
    val note: String?
)
