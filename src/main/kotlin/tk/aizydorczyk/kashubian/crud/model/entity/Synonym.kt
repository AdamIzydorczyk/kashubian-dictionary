package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "synonym")
data class Synonym(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synonym_id_generator")
    @SequenceGenerator(name = "synonym_id_generator", sequenceName = "synonym_id_sequence", allocationSize = 1)
    var id: Long,
    val note: String?,
    @Column(name = "synonym_id")
    var synonym: Long
)
