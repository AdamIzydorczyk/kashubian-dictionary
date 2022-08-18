package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "meaning")
data class Meaning(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "meaning_id_generator")
    @SequenceGenerator(name = "meaning_id_generator", sequenceName = "meaning_id_sequence", allocationSize = 1)
    override var id: Long,
    @Transient
    var translation: Translation?,
    val definition: String? = null,
    val origin: String? = null,
    @Column(name = "base_id")
    var base: Long? = null,
    @Column(name = "hyperonym_id")
    var hyperonym: Long? = null,
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
    val antonyms: List<Antonym> = emptyList(),
    @Column(name = "kashubian_entry_id")
    var kashubianEntry: Long
) : BaseEntity
