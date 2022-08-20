package tk.aizydorczyk.kashubian.crud.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "other")
data class Other(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "other_id_generator")
    @SequenceGenerator(name = "other_id_generator", sequenceName = "other_id_sequence", allocationSize = 1)
    override var id: Long,
    val note: String?,
    @Column(name = "other_id")
    var other: Long,
    @Column(name = "kashubian_entry_id")
    var kashubianEntry: Long

) : ChildEntity {
    override fun setParentId(parentId: Long) {
        kashubianEntry = parentId
    }
}
