package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class KashubianEntryRemover(val repository: KashubianEntryRepository) {

    @Transactional
    fun remove(entryId: Long) {
        repository.deleteEntryById(entryId)
    }
}