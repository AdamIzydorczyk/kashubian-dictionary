package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.model.entity.SoundFile

class KashubianEntrySoundFileDownloader(private val findSoundFileByEntryIdFunction: (Long) -> SoundFile) {
    fun download(entryId: Long): SoundFile =
        findSoundFileByEntryIdFunction.invoke(entryId)
}
