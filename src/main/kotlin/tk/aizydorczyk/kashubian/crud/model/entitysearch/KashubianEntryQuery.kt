package tk.aizydorczyk.kashubian.crud.model.entitysearch

import graphql.schema.DataFetchingEnvironment
import org.jooq.DSLContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.KASHUBIAN_ENTRY

@Controller
class KashubianEntryQuery(val dsl: DSLContext) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @QueryMapping
    fun findAllSearchKashubianEntries(env: DataFetchingEnvironment): Iterable<SearchKashubianEntry> {

        val fieldsRelations = mapOf("KashubianEntry.id" to KASHUBIAN_ENTRY.ID,
                "KashubianEntry.word" to KASHUBIAN_ENTRY.WORD,
                "KashubianEntry.variation" to KASHUBIAN_ENTRY.VARIATION)


        val databaseFields = env.selectionSet.fields.map { fieldsRelations[it.fullyQualifiedName] }

        return dsl.select(databaseFields)
            .from(KASHUBIAN_ENTRY)
            .apply { logger.info(this.sql) }
            .fetchInto(SearchKashubianEntry::class.java)
    }

}