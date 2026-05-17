package tk.aizydorczyk.kashubian.crud.domain

class ExampleRemover(private val deleteFunction: (Long) -> Unit) {
    fun remove(exampleId: Long) = deleteFunction(exampleId)
}
