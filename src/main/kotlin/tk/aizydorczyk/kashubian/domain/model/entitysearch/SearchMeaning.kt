package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "meaning")
data class SearchMeaning(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToMany(mappedBy = "meaning")
    val translation: Set<SearchTranslation> = emptySet(),
    val definition: String? = null,
    val origin: String? = null,
    @ManyToOne
    var base: SearchMeaning? = null,
    @ManyToOne
    var superordinate: SearchMeaning? = null,
    @OneToMany(mappedBy = "meaning")
    val proverbs: Set<SearchProverb> = emptySet(),
    @OneToMany(mappedBy = "meaning")
    val phrasalVerbs: Set<SearchPhrasalVerb> = emptySet(),
    @OneToMany(mappedBy = "meaning")
    val quotes: Set<SearchQuote> = emptySet(),
    @OneToMany(mappedBy = "meaning")
    val examples: Set<SearchExample> = emptySet(),
    @OneToMany(mappedBy = "meaning")
    val synonyms: Set<SearchSynonym> = emptySet(),
    @OneToMany(mappedBy = "meaning")
    val antonyms: Set<SearchAntonym> = emptySet(),

    @ManyToOne
    @JoinColumn(name = "kashubian_entry_id")
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchMeaning

        if (id != other.id) return false

        return true
    }
}
