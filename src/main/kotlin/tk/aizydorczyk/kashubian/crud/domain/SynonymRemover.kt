package tk.aizydorczyk.kashubian.crud.domain

class SynonymRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(synonymId: Long) = deleteFunction(synonymId)
}
