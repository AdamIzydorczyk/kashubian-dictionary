package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "phrasal_verb")
data class PhrasalVerb(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phrasal_verb_id_generator")
    @SequenceGenerator(name = "phrasal_verb_id_generator",
            sequenceName = "phrasal_verb_id_sequence",
            allocationSize = 1)
    var id: Long,
    val phrasalVerb: String,
    val note: String)
