package tk.aizydorczyk.kashubian.crud.model.entitysearch

import org.hibernate.annotations.Immutable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "phrasal_verb")
@Immutable
data class SearchPhrasalVerb(
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    val id: Long,
    val phrasalVerb: String,
    val note: String,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchPhrasalVerb

        if (id != other.id) return false

        return true
    }
}
