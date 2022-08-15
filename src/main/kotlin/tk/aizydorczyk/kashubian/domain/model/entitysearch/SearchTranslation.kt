package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "translation")
data class SearchTranslation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val polish: String?,
    val english: String?,
    val german: String?,
    val ukrainian: String?,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchTranslation

        if (id != other.id) return false

        return true
    }
}