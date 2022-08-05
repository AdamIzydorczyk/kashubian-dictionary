package tk.aizydorczyk.kashubian.domain

import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import javax.persistence.EntityManager

@Component
class KashubianEntryFinder(val entityManager: EntityManager) {
    fun findAll(): List<KashubianEntry> =
        entityManager.createQuery("from tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry",
                KashubianEntry::class.java)
            .resultList
}