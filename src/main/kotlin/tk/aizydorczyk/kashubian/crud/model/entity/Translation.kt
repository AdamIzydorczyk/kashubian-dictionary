package tk.aizydorczyk.kashubian.crud.model.entity

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
    val english: String?,
    val german: String?,
    val ukrainian: String?,
    @Column(name = "meaning_id")
    val meaning: Long
) : BaseEntity