package tk.aizydorczyk.kashubian.crud.model.entity

import com.fasterxml.jackson.databind.node.ObjectNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "kashubian_entry")
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class KashubianEntry(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "kashubian_entry_id_generator")
    @SequenceGenerator(name = "kashubian_entry_id_generator",
            sequenceName = "kashubian_entry_id_sequence",
            allocationSize = 1)
    override var id: Long,
    var createdAt: LocalDateTime?,
    var modifiedAt: LocalDateTime?,
    var createdBy: String?,
    var modifiedBy: String?,
    val word: String?,
    @Column(unique = true)
    var normalizedWord: String?,
    val note: String?,
    val priority: Boolean,
    @Enumerated(EnumType.STRING)
    val partOfSpeech: PartOfSpeechType?,
    @Enumerated(EnumType.STRING)
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var variation: ObjectNode?,
    @Column(name = "base_id")
    var base: Long? = null,
    @OneToMany
    @JoinColumn(name = "kashubian_entry_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val meanings: MutableList<Meaning> = mutableListOf(),
    @OneToMany
    @JoinColumn(name = "kashubian_entry_id", insertable = false, updatable = false, nullable = false)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    val others: MutableList<Other> = mutableListOf()
) : BaseEntity