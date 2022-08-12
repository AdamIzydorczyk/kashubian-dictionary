package tk.aizydorczyk.kashubian.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Meaning
import tk.aizydorczyk.kashubian.domain.model.entity.variation.VerbVariation
import javax.persistence.EntityManager

abstract class KashubianEntryApplyer(val entityManager: EntityManager) {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    protected fun persistAllEntryContentAndAssignRelations(entry: KashubianEntry): KashubianEntry {


        entry.variation.let{
            it!!.variation = objectMapper.valueToTree(VerbVariation())
            entityManager.persist(it)
        }

        entry.meanings.forEach {
            it.translation.let(entityManager::persist)
            it.proverbs.forEach(entityManager::persist)
            it.phrasalVerbs.forEach(entityManager::persist)
            it.quotes.forEach(entityManager::persist)
            it.examples.forEach(entityManager::persist)

            it.base = it.base?.let { base ->
                entityManager.find(Meaning::class.java, base.id)
            }

            it.superordinate = it.superordinate?.let { superordinate ->
                entityManager.find(Meaning::class.java, superordinate.id)
            }

            entityManager.persist(it)
        }
        return entry
    }
}