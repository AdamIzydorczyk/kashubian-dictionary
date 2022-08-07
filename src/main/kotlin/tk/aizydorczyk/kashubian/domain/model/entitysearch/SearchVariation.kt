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
}
