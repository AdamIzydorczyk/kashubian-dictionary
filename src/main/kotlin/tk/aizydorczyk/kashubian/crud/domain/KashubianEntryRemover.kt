package tk.aizydorczyk.kashubian.crud.domain


class KashubianEntryRemover(private val deleteEntryByIdFunction: (Long) -> Unit) {
    fun remove(entryId: Long) {
        deleteEntryByIdFunction.invoke(entryId)
    }
}