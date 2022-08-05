package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "variation")
data class Variation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nominative: String?,
    val genitive: String?,
    val dative: String?,
    val accusative: String?,
    val instrumental: String?,
    val locative: String?,
    val vocative: String?,
    val nominativePlural: String?,
    val genitivePlural: String?,
    val dativePlural: String?,
    val accusativePlural: String?,
    val instrumentalPlural: String?,
    val locativePlural: String?,
    val vocativePlural: String?
)
