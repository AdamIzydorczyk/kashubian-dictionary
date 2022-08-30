package tk.aizydorczyk.kashubian.crud.model.entitysearch

import org.hibernate.annotations.Immutable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "quote")
@Immutable
data class SearchQuote(
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    val id: Long,
    val quote: String,
    val note: String,
    @ManyToOne
    @JoinColumn(name = "meaning_id")
    val meaning: SearchMeaning
)
