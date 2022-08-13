package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "antonym")
data class SearchAntonym(
    @Id
    val id: Long?,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "antonym_id")
    var antonym: SearchMeaning,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchAntonym

        if (id != other.id) return false

        return true
    }
}
