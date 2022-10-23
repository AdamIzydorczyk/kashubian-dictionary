package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "idiom")
data class Idiom(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "idiom_id_generator")
    @SequenceGenerator(name = "idiom_id_generator",
            sequenceName = "idiom_id_sequence",
            allocationSize = 1)
    override var id: Long,
    val idiom: String,
    val note: String,
    @Column(name = "meaning_id")
    var meaning: Long) : ChildEntity {
    override fun setParentId(parentId: Long) {
        meaning = parentId
    }
}
