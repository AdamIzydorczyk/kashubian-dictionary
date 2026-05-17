package tk.aizydorczyk.kashubian.crud.model.value

class AnnotationConstants {
    companion object {
        const val KASHUBIAN_ENTRY_PATH = "/kashubian-entry"
        const val GRAPH_QL_PATH = "/graphql"
        const val ENTRY_ID = "entryId"
        const val ENTRY_ID_PATH = "/{${ENTRY_ID}}"
        const val FILE_PATH = "/{${ENTRY_ID}}/file"
        const val MEANING_ID = "meaningId"
        const val MEANING_PATH = "/meaning"
        const val MEANING_ID_PATH = "/{${MEANING_ID}}"

        const val ANTONYM_ID = "antonymId"
        const val ANTONYM_PATH = "/antonym"
        const val ANTONYM_ID_PATH = "/{${ANTONYM_ID}}"

        const val SYNONYM_ID = "synonymId"
        const val SYNONYM_PATH = "/synonym"
        const val SYNONYM_ID_PATH = "/{${SYNONYM_ID}}"

        const val EXAMPLE_ID = "exampleId"
        const val EXAMPLE_PATH = "/example"
        const val EXAMPLE_ID_PATH = "/{${EXAMPLE_ID}}"

        const val IDIOM_ID = "idiomId"
        const val IDIOM_PATH = "/idiom"
        const val IDIOM_ID_PATH = "/{${IDIOM_ID}}"

        const val PROVERB_ID = "proverbId"
        const val PROVERB_PATH = "/proverb"
        const val PROVERB_ID_PATH = "/{${PROVERB_ID}}"

        const val QUOTE_ID = "quoteId"
        const val QUOTE_PATH = "/quote"
        const val QUOTE_ID_PATH = "/{${QUOTE_ID}}"

        const val TRANSLATION_PATH = "/translation"

        const val OTHER_ID = "otherId"
        const val OTHER_PATH = "/other"
        const val OTHER_ID_PATH = "/{${OTHER_ID}}"
    }
}