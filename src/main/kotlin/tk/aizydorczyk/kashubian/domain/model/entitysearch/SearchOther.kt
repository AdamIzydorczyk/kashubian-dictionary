package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "other")
data class SearchOther(
    @Id
    val id: Long,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "other_id")
    var other: SearchKashubianEntry,
    @ManyToOne
    @JoinColumn(name = "kashubian_entry_id")
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchOther

        if (id != other.id) return false

        return true
    }
}
