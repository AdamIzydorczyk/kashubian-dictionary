package tk.aizydorczyk.kashubian.domain.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sound_file")
data class SoundFile(
    @Id
    val id: Long,
    val fileName: String,
    val type: String,
    @Column(columnDefinition = "bytea")
    val file: ByteArray,
    @Column(name = "kashubian_entry_id")
    val kashubianEntry: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SoundFile

        if (id != other.id) return false
        if (fileName != other.fileName) return false
        if (type != other.type) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + file.contentHashCode()
        return result
    }
}
