package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "phrasal_verb")
data class PhrasalVerbSearch(
    @Id
    val id: Long,
    val phrasalVerb: String,
    val note: String,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: MeaningSearch
)
