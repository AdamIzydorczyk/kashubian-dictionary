package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "antonym")
data class Antonym(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "antonym_id_generator")
    @SequenceGenerator(name = "antonym_id_generator", sequenceName = "antonym_id_sequence", allocationSize = 1)
    val id: Long,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "antonym_id")
    var antonym: Meaning
)
