package tk.aizydorczyk.kashubian.crud.domain

class OtherRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(otherId: Long) = deleteFunction(otherId)
}
