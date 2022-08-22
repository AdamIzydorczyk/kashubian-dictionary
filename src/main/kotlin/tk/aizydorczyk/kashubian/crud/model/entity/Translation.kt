package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "translation")
data class Translation(
    @Id
    var id: Long,
    val polish: String?,
    val normalizedPolish: String?,
    val english: String?,
    val normalizedEnglish: String?,
    val german: String?,
    val normalizedGerman: String?,
    val ukrainian: String?,
    val normalizedUkrainian: String?,

    @Column(name = "meaning_id")
    val meaning: Long
)