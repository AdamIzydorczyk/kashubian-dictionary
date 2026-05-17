package tk.aizydorczyk.kashubian.crud.domain

class ProverbRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(proverbId: Long) = deleteFunction(proverbId)
}
