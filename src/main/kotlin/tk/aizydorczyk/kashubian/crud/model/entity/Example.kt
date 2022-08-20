package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "example")
data class Example(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "example_id_generator")
    @SequenceGenerator(name = "example_id_generator", sequenceName = "example_id_sequence", allocationSize = 1)
    override var id: Long,
    val example: String?,
    val note: String?,
    @Column(name = "meaning_id")
    var meaning: Long
) : ChildEntity {
    override fun setParentId(parentId: Long) {
        meaning = parentId
    }
}
