package tk.aizydorczyk.kashubian.crud.domain

class IdiomRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(idiomId: Long) = deleteFunction(idiomId)
}
