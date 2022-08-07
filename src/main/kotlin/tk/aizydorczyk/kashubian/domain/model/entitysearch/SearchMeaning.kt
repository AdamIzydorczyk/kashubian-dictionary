package tk.aizydorczyk.kashubian.domain.model.entitysearch

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "meaning")
data class SearchMeaning(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    @JoinColumn(name = "translation_id")
    val translation: SearchTranslation? = null,
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
    @ManyToOne
    @JoinColumn(name = "kashubian_entry_id")
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
}
