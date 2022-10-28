package tk.aizydorczyk.kashubian.crud.model.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sound_file")
data class SoundFile(
    @Id
    var id: Long,
    val createdAt: LocalDateTime?,
    val createdBy: String?,
    val fileName: String,
    val type: String,
    @Column(columnDefinition = "bytea")
    val file: ByteArray,
    @Column(name = "kashubian_entry_id")
    val kashubianEntry: Long
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SoundFile) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id.hashCode()
}
