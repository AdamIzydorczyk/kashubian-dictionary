package tk.aizydorczyk.kashubian.crud.model.entity

import tk.aizydorczyk.kashubian.crud.extension.normalize
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "translation")
data class Translation(
    @Id
    override var id: Long,
    val polish: String?,
    val normalizedPolish: String?,
    val english: String?,
    val normalizedEnglish: String?,
    val german: String?,
    val normalizedGerman: String?,
    val ukrainian: String?,
    val normalizedUkrainian: String?,

    @Column(name = "meaning_id")
    var meaning: Long
) : ChildEntity {
    override fun setParentId(parentId: Long) {
        meaning = parentId
    }

    fun copyWitchNormalizedFieldsAndAssignedId(meaningId: Long) = Translation(
            id = meaningId,
            polish = this.polish,
            normalizedPolish = this.polish?.normalize(),
            english = this.english,
            normalizedEnglish = this.english?.normalize(),
            german = this.german,
            normalizedGerman = this.german?.normalize(),
            ukrainian = this.ukrainian,
            normalizedUkrainian = this.ukrainian?.normalize(),
            meaning = meaningId)

}