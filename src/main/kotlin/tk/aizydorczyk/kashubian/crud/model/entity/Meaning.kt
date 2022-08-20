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
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val proverbs: MutableList<Proverb> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val phrasalVerbs: MutableList<PhrasalVerb> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val quotes: MutableList<Quote> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val examples: MutableList<Example> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val synonyms: MutableList<Synonym> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    val antonyms: MutableList<Antonym> = mutableListOf(),
    @Column(name = "kashubian_entry_id")
    var kashubianEntry: Long
) : ChildEntity {
    override fun setParentId(parentId: Long) {
        kashubianEntry = parentId
    }
}
