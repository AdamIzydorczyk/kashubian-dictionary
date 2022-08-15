package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "meaning")
data class Meaning(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meaning_id_generator")
    @SequenceGenerator(name = "meaning_id_generator", sequenceName = "meaning_id_sequence", allocationSize = 1)
    var id: Long,
    @Transient
    var translation: Translation?,
    val definition: String? = null,
    val origin: String? = null,
    @Column(name = "base_id")
    var base: Long? = null,
    @Column(name = "superordinate_id")
    var superordinate: Long? = null,
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val proverbs: List<Proverb> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val phrasalVerbs: List<PhrasalVerb> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val quotes: List<Quote> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val examples: List<Example> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val synonyms: List<Synonym> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val antonyms: List<Antonym> = emptyList()
)
