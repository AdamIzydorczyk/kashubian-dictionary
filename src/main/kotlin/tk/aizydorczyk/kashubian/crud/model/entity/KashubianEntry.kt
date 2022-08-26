package tk.aizydorczyk.kashubian.crud.model.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
data class KashubianEntry(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "kashubian_entry_id_generator")
    @SequenceGenerator(name = "kashubian_entry_id_generator",
            sequenceName = "kashubian_entry_id_sequence",
            allocationSize = 1)
    override var id: Long,
    val word: String?,
    @Column(unique = true)
    var normalizedWord: String?,
    val note: String?,
    val priority: Boolean,
    @Enumerated(EnumType.STRING)
    val partOfSpeech: PartOfSpeechType?,
    @Enumerated(EnumType.STRING)
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @Transient
    var variation: Variation?,
    @OneToMany
    @JoinColumn(name = "kashubian_entry_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val meanings: MutableList<Meaning> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "kashubian_entry_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val others: MutableList<Other> = mutableListOf()
) : BaseEntity