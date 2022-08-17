package tk.aizydorczyk.kashubian.crud.model.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import javax.persistence.CascadeType
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
    @Column(unique = true)
    val word: String?,
    @Enumerated(EnumType.STRING)
    val partOfSpeech: PartOfSpeechType?,
    @Enumerated(EnumType.STRING)
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @Transient
    var soundFile: SoundFile?,
    @Transient
    var variation: Variation?,
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "kashubian_entry_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val meanings: List<Meaning> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "kashubian_entry_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val others: List<Other> = emptyList()
) : BaseEntity