package tk.aizydorczyk.kashubian.domain.model.entitysearch

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
data class MeaningSearch(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    @JoinColumn(name = "translation_id")
    val translation: TranslationSearch? = null,
    val definition: String? = null,
    val origin: String? = null,
    @ManyToOne
    var base: MeaningSearch? = null,
    @ManyToOne
    var superordinate: MeaningSearch? = null,
    @OneToMany(mappedBy = "meaning")
    val proverbs: List<ProverbSearch> = emptyList(),
    @OneToMany(mappedBy = "meaning")
    val phrasalVerbs: List<PhrasalVerbSearch> = emptyList(),
    @OneToMany(mappedBy = "meaning")
    val quotes: List<QuoteSearch> = emptyList(),
    @OneToMany(mappedBy = "meaning")
    val examples: List<ExampleSearch> = emptyList(),

    @ManyToOne
    @JoinColumn(name = "kashubian_entry_id")
    val kashubianEntry: KashubianEntrySearch
)
