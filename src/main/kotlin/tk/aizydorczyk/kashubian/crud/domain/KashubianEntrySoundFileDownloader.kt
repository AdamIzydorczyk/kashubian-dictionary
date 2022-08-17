package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile

@Component
class KashubianEntrySoundFileDownloader(val repository: KashubianEntryRepository) {
    @Transactional(readOnly = true)
    fun download(entryId: Long): SoundFile =
        repository.findSoundFileByEntryId(entryId)
}
