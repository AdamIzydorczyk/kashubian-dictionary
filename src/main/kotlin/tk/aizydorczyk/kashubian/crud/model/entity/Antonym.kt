package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "antonym")
data class Antonym(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "antonym_id_generator")
    @SequenceGenerator(name = "antonym_id_generator", sequenceName = "antonym_id_sequence", allocationSize = 1)
    override var id: Long,
    val note: String?,
    @Column(name = "antonym_id")
    var antonym: Long,
    @Column(name = "meaning_id")
    var meaning: Long
) : ChildEntity {
    override fun setParentId(parentId: Long) {
        meaning = parentId
    }
}
