package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "antonym")
data class Antonym(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "antonym_id_generator")
    @SequenceGenerator(name = "antonym_id_generator", sequenceName = "antonym_id_sequence", allocationSize = 1)
    var id: Long,
    val note: String?,
    @Column(name = "antonym_id")
    var antonym: Long
)
