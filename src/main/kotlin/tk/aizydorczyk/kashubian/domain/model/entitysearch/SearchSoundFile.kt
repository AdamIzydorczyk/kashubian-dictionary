package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "sound_file")
data class SearchSoundFile(
    @Id
    val id: Long,
    val fileName: String,
    val type: String,
    @OneToOne(mappedBy = "soundFile", fetch = FetchType.LAZY)
    val kashubianEntry: SearchKashubianEntry
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchSoundFile

        if (id != other.id) return false

        return true
    }
}
