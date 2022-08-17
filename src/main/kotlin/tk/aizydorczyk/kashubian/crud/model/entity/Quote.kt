package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "quote")
data class Quote(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_generator")
    @SequenceGenerator(name = "quote_id_generator", sequenceName = "quote_id_sequence", allocationSize = 1)
    override var id: Long,
    val quote: String,
    val note: String
) : BaseEntity
