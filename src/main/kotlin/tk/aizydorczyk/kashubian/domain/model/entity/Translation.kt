package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "variation")
data class Translation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val polish: String?,
    val english: String?,
    val german: String?,
    val ukrainian: String?
)