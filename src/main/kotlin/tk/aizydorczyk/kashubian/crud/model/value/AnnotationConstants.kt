package tk.aizydorczyk.kashubian.crud.model.value

class AnnotationConstants {
    companion object {
        const val KASHUBIAN_ENTRY_PATH = "/kashubian-entry"
        const val ENTRY_ID = "entryId"
        const val ENTRY_ID_PATH = "/{${ENTRY_ID}}"
        const val FILE_PATH = "/{${ENTRY_ID}}/file"
        const val MEANING_ID = "meaningId"
    }
}