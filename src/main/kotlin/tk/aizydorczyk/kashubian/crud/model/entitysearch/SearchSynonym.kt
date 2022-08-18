package tk.aizydorczyk.kashubian.crud.model.entitysearch

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "synonym")
data class SearchSynonym(
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    val id: Long,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "synonym_id")
    var synonym: SearchMeaning,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchSynonym

        if (id != other.id) return false

        return true
    }
}
