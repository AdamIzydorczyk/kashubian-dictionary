package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
data class SearchKashubianEntry(
    @Id
    var id: Long,
    @Column(unique = true)
    val word: String?,
    val soundFileUrl: String?,
    val note: String?,
    val partOfSpeech: String?,
    @OneToOne
    @JoinColumn(name = "variation_id")
    val variation: SearchVariation?,
    @OneToMany(mappedBy = "kashubianEntry")
    val meanings: Set<SearchMeaning> = emptySet()
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchKashubianEntry

        if (id != other.id) return false

        return true
    }
}