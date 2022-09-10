package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
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
import org.postgresql.util.PGobject
import org.simpleflatmapper.converter.ContextualConverter
import org.simpleflatmapper.jooq.SelectQueryMapperFactory
import org.simpleflatmapper.map.property.ConverterProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Routines
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.KASHUBIAN_ENTRY
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.MEANING
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.OTHER

@Controller
class KashubianEntryQuery(private val dsl: DSLContext, private val objectMapper: ObjectMapper) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    private final val fieldsRelations =
        mapOf("KashubianEntryPaged.select/KashubianEntry.id" to KASHUBIAN_ENTRY.`as`("ke").ID.`as`("entry_id"),
                "KashubianEntryPaged.select/KashubianEntry.word" to KASHUBIAN_ENTRY.`as`("ke").WORD.`as`("entry_word"),
                "KashubianEntryPaged.select/KashubianEntry.normalizedWord" to KASHUBIAN_ENTRY.`as`("ke").NORMALIZED_WORD.`as`(
                        "entry_normalized_word"),
                "KashubianEntryPaged.select/KashubianEntry.variation" to KASHUBIAN_ENTRY.`as`("ke").VARIATION.`as`("entry_variation"),
                "KashubianEntryPaged.select/KashubianEntry.priority" to KASHUBIAN_ENTRY.`as`("ke").PRIORITY.`as`("entry_priority"),
                "KashubianEntryPaged.select/KashubianEntry.note" to KASHUBIAN_ENTRY.`as`("ke").NOTE.`as`("entry_note"),
                "KashubianEntryPaged.select/KashubianEntry.partOfSpeech" to KASHUBIAN_ENTRY.`as`("ke").PART_OF_SPEECH.`as`(
                        "entry_part_of_speech"),
                "KashubianEntryPaged.select/KashubianEntry.partOfSpeechSubType" to KASHUBIAN_ENTRY.`as`("ke").PART_OF_SPEECH_SUB_TYPE.`as`(
                        "entry_part_of_speech_sub_type"),
                "KashubianEntryPaged.select/KashubianEntry.meaningsCount" to field(selectCount().from(MEANING).where(
                        MEANING.KASHUBIAN_ENTRY_ID.eq(KASHUBIAN_ENTRY.`as`("ke").ID))).`as`("meanings_count"),
                "KashubianEntryPaged.select/KashubianEntry.bases" to field(select(Routines.findBases(KASHUBIAN_ENTRY.`as`(
                        "ke").ID))).`as`(
                        "bases"),
                "KashubianEntryPaged.select/KashubianEntry.derivatives" to field(select(Routines.findDerivatives(
                        KASHUBIAN_ENTRY.`as`("ke").ID))).`as`("derivatives"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.id" to OTHER.ID.`as`("other_id"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.note" to OTHER.NOTE.`as`("other_note"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.other/KashubianEntrySimplified.id" to KASHUBIAN_ENTRY.`as`(
                        "other_entry").ID.`as`("other_entry_id"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.other/KashubianEntrySimplified.word" to KASHUBIAN_ENTRY.`as`(
                        "other_entry").WORD.`as`("other_entry_word")
        )

    @QueryMapping
    fun findAllSearchKashubianEntries(
        @Argument("page") page: Page?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntryPaged {

        val selectedFields: MutableList<SelectFieldOrAsterisk?> = env.selectionSet.fields
            .map { fieldsRelations[it.fullyQualifiedName] }
            .toMutableList()

        val denseRank = denseRank().over(orderBy(KASHUBIAN_ENTRY.`as`("ke").ID)).`as`("dense_rank")
        selectedFields.add(denseRank)

        val ordersBy = env.selectionSet.fields.filter { it.arguments.isNotEmpty() }.map {
            when (it.arguments["orderBy"]) {
                "ASC" -> fieldsRelations[it.fullyQualifiedName]!!.asc()
                else -> fieldsRelations[it.fullyQualifiedName]!!.desc()
            }
        }

        val entriesCount = when (isContainsPaginationFields(env.selectionSet.fields)) {
            true -> dsl.fetchCount(KASHUBIAN_ENTRY)
            false -> 0
        }


        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageEnd = pageStart + limit

        val pageCount: Int = (entriesCount + limit - 1) / limit

        val mapper = SelectQueryMapperFactory.newInstance().ignorePropertyNotFound()
            .addColumnProperty("bases", ConverterProperty.of(ContextualConverter<PGobject, ArrayNode?> { value, _ ->
                value?.let { json -> objectMapper.readTree(json.value).let { it as ArrayNode } }
            }))
            .addColumnProperty("derivatives",
                    ConverterProperty.of(ContextualConverter<PGobject, ArrayNode?> { value, _ ->
                        value?.let { json -> objectMapper.readTree(json.value).let { it as ArrayNode } }
                    }))
            .addColumnProperty("variation",
                    ConverterProperty.of(ContextualConverter<PGobject, ObjectNode?> { value, _ ->
                        value?.let { json -> objectMapper.readTree(json.value).let { it as ObjectNode } }
                    }))
            .newMapper(KashubianEntryGraphQL::class.java)

        return dsl.select(asterisk())
            .from(select(selectedFields)
                .from(KASHUBIAN_ENTRY.`as`("ke"))
                .join(OTHER).on(KASHUBIAN_ENTRY.`as`("ke").ID.eq(OTHER.KASHUBIAN_ENTRY_ID))
                .join(KASHUBIAN_ENTRY.`as`("other_entry"))
                .on(KASHUBIAN_ENTRY.`as`("other_entry").ID.eq(OTHER.OTHER_ID.`as`("other_id")))
                .orderBy(ordersBy))
            .where(field(name("dense_rank")).between(pageStart, pageEnd))
            .let { KashubianEntryPaged(pageCount, entriesCount, mapper.asList(it)) }
    }

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any { it.fullyQualifiedName == "KashubianEntryPaged.total" || it.fullyQualifiedName == "KashubianEntryPaged.pages" }

}