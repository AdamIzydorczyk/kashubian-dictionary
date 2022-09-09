package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import graphql.schema.DataFetchingEnvironment
import graphql.schema.SelectedField
import org.jooq.DSLContext
import org.jooq.SelectFieldOrAsterisk
import org.jooq.impl.DSL.asterisk
import org.jooq.impl.DSL.denseRank
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.name
import org.jooq.impl.DSL.orderBy
import org.jooq.impl.DSL.select
import org.jooq.impl.DSL.selectCount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.KASHUBIAN_ENTRY
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.MEANING

@Controller
class KashubianEntryQuery(val dsl: DSLContext) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @QueryMapping
    fun findAllSearchKashubianEntries(
        @Argument("page") page: Page?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntryPaged {

        val fieldsRelations = mapOf("KashubianEntryPaged.select/KashubianEntry.id" to KASHUBIAN_ENTRY.ID,
                "KashubianEntryPaged.select/KashubianEntry.word" to KASHUBIAN_ENTRY.WORD,
                "KashubianEntryPaged.select/KashubianEntry.variation" to KASHUBIAN_ENTRY.VARIATION,
                "KashubianEntryPaged.select/KashubianEntry.meaningsCount" to field(selectCount().from(MEANING).where(
                        MEANING.KASHUBIAN_ENTRY_ID.eq(KASHUBIAN_ENTRY.ID))).`as`("meanings_count"))

        val databaseFields: MutableList<SelectFieldOrAsterisk?> = env.selectionSet.fields
            .map { fieldsRelations[it.fullyQualifiedName] }
            .toMutableList()


        val entriesCount = when (isContainsPaginationFields(env.selectionSet.fields)) {
            true -> dsl.fetchCount(KASHUBIAN_ENTRY)
            false -> 0
        }


        val denseRank = denseRank().over(orderBy(KASHUBIAN_ENTRY.ID)).`as`("dense_rank")
        databaseFields.add(denseRank)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageEnd = pageStart + limit

        val pageCount: Int = (entriesCount + limit - 1) / limit


        return dsl.select(asterisk())
            .from(select(databaseFields)
                .from(KASHUBIAN_ENTRY))
            .where(field(name("dense_rank")).between(pageStart, pageEnd))
            .apply { logger.info(this.sql) }
            .fetchInto(SearchKashubianEntry::class.java).let { KashubianEntryPaged(pageCount, entriesCount, it) }
    }

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any { it.fullyQualifiedName == "KashubianEntryPaged.total" || it.fullyQualifiedName == "KashubianEntryPaged.pages" }

}