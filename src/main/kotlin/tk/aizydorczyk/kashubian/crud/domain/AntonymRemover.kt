package tk.aizydorczyk.kashubian.crud.domain

class AntonymRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(antonymId: Long) = deleteFunction(antonymId)
}
