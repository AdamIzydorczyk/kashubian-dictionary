package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "variation")
data class SearchVariation(
    @Id
    val id: Long,
    val nominative: String?,
    val genitive: String?,
    val dative: String?,
    val accusative: String?,
    val instrumental: String?,
    val locative: String?,
    val vocative: String?,
    val nominativePlural: String?,
    val genitivePlural: String?,
    val dativePlural: String?,
    val accusativePlural: String?,
    val instrumentalPlural: String?,
    val locativePlural: String?,
    val vocativePlural: String?,

    @OneToOne(mappedBy = "variation")
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchVariation

        if (id != other.id) return false

        return true
    }
}
