package tk.aizydorczyk.kashubian.crud.domain

class KashubianEntrySoundFileRemover(private val removeSoundFileByEntryIdFunction: (Long) -> Unit) {
    fun remove(entryId: Long) {
        removeSoundFileByEntryIdFunction.invoke(entryId)
    }
}