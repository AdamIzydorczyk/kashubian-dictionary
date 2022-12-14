package tk.aizydorczyk.kashubian.crud.model.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
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
    @Column(name = "hyperonym_id")
    var hyperonym: Long? = null,
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val proverbs: MutableList<Proverb> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val idioms: MutableList<Idiom> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val quotes: MutableList<Quote> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val examples: MutableList<Example> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val synonyms: MutableList<Synonym> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "meaning_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val antonyms: MutableList<Antonym> = mutableListOf(),
    @Column(name = "kashubian_entry_id")
    var kashubianEntry: Long
) : ChildEntity {
    override fun setParentId(parentId: Long) {
        kashubianEntry = parentId
    }
}
