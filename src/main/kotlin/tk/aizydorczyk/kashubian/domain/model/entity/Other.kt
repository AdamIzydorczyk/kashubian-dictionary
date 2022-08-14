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
@Table(name = "other")
data class Other(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meaning_id_generator")
    @SequenceGenerator(name = "meaning_id_generator", sequenceName = "meaning_id_sequence", allocationSize = 1)
    val id: Long,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "other_id")
    var other: KashubianEntry
)
