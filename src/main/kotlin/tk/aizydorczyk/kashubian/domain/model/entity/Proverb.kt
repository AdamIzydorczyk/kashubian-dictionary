package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "proverb")
data class Proverb(@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
val id: Long,
    val proverb: String,
    val note: String)
