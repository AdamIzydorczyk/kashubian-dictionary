package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Quote
import javax.persistence.EntityManager

class QuoteUpdater(private val entityManager: EntityManager) {

    fun update(quoteId: Long, updated: Quote): Quote {
        val old = entityManager.find(Quote::class.java, quoteId)
        updated.id = quoteId
        updated.meaning = old.meaning
        entityManager.merge(updated)
        return updated
    }
}
