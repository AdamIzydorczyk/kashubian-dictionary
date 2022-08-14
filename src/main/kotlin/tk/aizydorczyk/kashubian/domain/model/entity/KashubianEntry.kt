package tk.aizydorczyk.kashubian.domain.model.entity

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechType
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
data class KashubianEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kashubian_entry_id_generator")
    @SequenceGenerator(name = "kashubian_entry_id_generator",
            sequenceName = "kashubian_entry_id_sequence",
            allocationSize = 1)
    var id: Long,
    @Column(unique = true)
    val word: String?,
    @Enumerated(EnumType.STRING)
    val partOfSpeech: PartOfSpeechType?,
    @Enumerated(EnumType.STRING)
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @OneToOne(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "sound_file_id")
    var soundFile: SoundFile?,
    @OneToOne(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "variation_id")
    val variation: Variation?,
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "kashubian_entry_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val meanings: List<Meaning> = emptyList(),
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "kashubian_entry_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val others: List<Other> = emptyList()
)