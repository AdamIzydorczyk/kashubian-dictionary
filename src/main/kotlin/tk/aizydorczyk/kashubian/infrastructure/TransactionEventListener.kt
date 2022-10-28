package tk.aizydorczyk.kashubian.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tk.aizydorczyk.kashubian.crud.event.KashubianEntryApplicationEvent
import tk.aizydorczyk.kashubian.crud.model.entity.Event
import javax.persistence.EntityManager


@Component
class TransactionEventListener(
    val entityManager: EntityManager,
    val objectMapper: ObjectMapper) {

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun handleEvent(event: KashubianEntryApplicationEvent) {
        entityManager.persist(Event(event = objectMapper.valueToTree(event),
                eventType = event.eventType(),
                invokedAt = event.auditingInformation.executionTime,
                invokedBy = event.auditingInformation.userName))
    }
}