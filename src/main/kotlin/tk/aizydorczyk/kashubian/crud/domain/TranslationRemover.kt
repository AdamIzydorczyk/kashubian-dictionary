package tk.aizydorczyk.kashubian.crud.domain

class TranslationRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(meaningId: Long) = deleteFunction(meaningId)
}
