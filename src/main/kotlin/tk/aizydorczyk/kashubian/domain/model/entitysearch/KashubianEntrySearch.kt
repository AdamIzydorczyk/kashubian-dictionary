package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
data class KashubianEntrySearch(
    @Id
    var id: Long,
    @Column(unique = true)
    val word: String?,
    val soundFileUrl: String?,
    val note: String?,
    val partOfSpeech: String?,
    val genderNounType: String?,
    @OneToOne
    @JoinColumn(name = "variation_id")
    val variation: VariationSearch?,
    @OneToMany(mappedBy = "kashubianEntry")
    val meanings: List<MeaningSearch> = emptyList()
)