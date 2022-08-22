package tk.aizydorczyk.kashubian.crud.model.value

class AnnotationsConstants {
    companion object {
        const val DEFAULT_ENTITY_MANAGER = "defaultEntityManager"
        const val DEFAULT_ENTITY_MANAGER_FACTORY = "${DEFAULT_ENTITY_MANAGER}Factory"
        const val GRAPHQL_ENTITY_MANAGER = "graphqlEntityManager"
        const val GRAPHQL_ENTITY_MANAGER_FACTORY = "${GRAPHQL_ENTITY_MANAGER}Factory"
        const val KASHUBIAN_ENTRY_PATH = "kashubian-entry"
        const val ENTRY_ID = "entryId"
        const val ENTRY_ID_PATH = "/{${ENTRY_ID}}"
        const val FILE_PATH = "/{${ENTRY_ID}}/file"
        const val MEANING_ID = "meaningId"
    }
}