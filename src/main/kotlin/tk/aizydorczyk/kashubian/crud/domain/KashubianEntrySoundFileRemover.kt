package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class KashubianEntrySoundFileRemover(val repository: KashubianEntryRepository) {
    @Transactional
    fun remove(entryId: Long) {
        repository.removeSoundFileByEntryId(entryId)
    }
}