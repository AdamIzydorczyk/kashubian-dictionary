package tk.aizydorczyk.kashubian.crud.model.entitysearch

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "example")
data class SearchExample(
    @Id
    val id: Long,
    val example: String?,
    val note: String?,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchExample

        if (id != other.id) return false

        return true
    }
}
