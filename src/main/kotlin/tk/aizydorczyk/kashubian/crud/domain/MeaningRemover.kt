package tk.aizydorczyk.kashubian.crud.domain

class MeaningRemover(private val deleteMeaningByIdFunction: (Long) -> Unit) {
    fun remove(meaningId: Long) {
        deleteMeaningByIdFunction.invoke(meaningId)
    }
}
