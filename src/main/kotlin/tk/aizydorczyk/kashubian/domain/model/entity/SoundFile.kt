package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "sound_file")
data class SoundFile(
    @Id
    val id: Long,
    val fileName: String,
    val type: String,
    @Lob
    val file: ByteArray
)
