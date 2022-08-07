package tk.aizydorczyk.kashubian.domain.model.entitysearch

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
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

    @OneToOne(mappedBy = "translation")
    val meaning: SearchMeaning
)