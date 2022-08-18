package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "quote")
data class Quote(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "quote_id_generator")
    @SequenceGenerator(name = "quote_id_generator", sequenceName = "quote_id_sequence", allocationSize = 1)
    override var id: Long,
    val quote: String,
    val note: String,
    @Column(name = "meaning_id")
    var meaning: Long
) : BaseEntity
