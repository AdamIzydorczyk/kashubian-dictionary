package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "proverb")
data class Proverb(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proverb_id_generator")
    @SequenceGenerator(name = "proverb_id_generator", sequenceName = "proverb_id_sequence", allocationSize = 1)
    var id: Long,
    val proverb: String,
    val note: String)
