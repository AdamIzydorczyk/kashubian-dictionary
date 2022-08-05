package tk.aizydorczyk.kashubian.domain.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "quote")
data class Quote(@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
val id: Long,
    val quote: String,
    val note: String)
