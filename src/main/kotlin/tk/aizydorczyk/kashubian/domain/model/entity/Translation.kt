package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "translation")
data class Translation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_id_generator")
    @SequenceGenerator(name = "translation_id_generator", sequenceName = "translation_id_sequence", allocationSize = 1)
    val id: Long,
    val polish: String?,
    val english: String?,
    val german: String?,
    val ukrainian: String?
)