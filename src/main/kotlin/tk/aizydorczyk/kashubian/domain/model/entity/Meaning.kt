package tk.aizydorczyk.kashubian.domain.model.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "meaning")
data class Meaning(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meaning_id_generator")
    @SequenceGenerator(name = "meaning_id_generator", sequenceName = "meaning_id_sequence", allocationSize = 1)
    val id: Long,
    @OneToOne(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val translation: Translation? = null,
    val definition: String? = null,
    val origin: String? = null,
    @ManyToOne
    var base: Meaning? = null,
    @ManyToOne
    var superordinate: Meaning? = null,
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    val proverbs: List<Proverb> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val phrasalVerbs: List<PhrasalVerb> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val quotes: List<Quote> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val examples: List<Example> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val synonyms: MutableList<Synonym> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val antonyms: MutableList<Antonym> = mutableListOf()
)
