package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
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
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
data class KashubianEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(unique = true)
    val word: String?,
    val soundFileUrl: String?,
    val note: String?,
    @Enumerated(EnumType.STRING)
    val partOfSpeech: PartOfSpeechType?,
    @Enumerated(EnumType.STRING)
    val genderNounType: GenderNounType?,
    @OneToOne(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val variation: Variation?,
    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "kashubian_entry_id")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val meanings: List<Meaning> = emptyList()
)