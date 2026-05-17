package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.Quote
import javax.persistence.EntityManager

class QuoteCreator(private val entityManager: EntityManager) {

    fun create(meaningId: Long, quote: Quote): Quote {
        quote.meaning = meaningId
        entityManager.persist(quote)
        return quote
    }
}
