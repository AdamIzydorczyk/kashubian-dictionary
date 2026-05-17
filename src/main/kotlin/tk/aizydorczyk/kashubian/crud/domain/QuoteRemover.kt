package tk.aizydorczyk.kashubian.crud.domain

class QuoteRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(quoteId: Long) = deleteFunction(quoteId)
}
