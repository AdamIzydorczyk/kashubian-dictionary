package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import graphql.schema.DataFetchingEnvironment
import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.impl.DSL.asterisk
import org.jooq.impl.DSL.denseRank
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.name
import org.jooq.impl.DSL.orderBy
import org.jooq.impl.DSL.select
import org.jooq.impl.DSL.selectCount
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
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
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables.TRANSLATION

@Controller
class KashubianEntryQuery(private val dsl: DSLContext, private val objectMapper: ObjectMapper) {
    val logger: Logger = LoggerFactory.getLogger(javaClass)


    private final val joins = mapOf(
            "KashubianEntryPaged.select/KashubianEntry.others" to Triple(OTHER,
                    KASHUBIAN_ENTRY.`as`("entry").ID.eq(OTHER.KASHUBIAN_ENTRY_ID), OTHER.ID.`as`("other_id")),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.other" to Triple(KASHUBIAN_ENTRY.`as`("other_entry"),
                    KASHUBIAN_ENTRY.`as`("other_entry").ID.eq(OTHER.OTHER_ID.`as`("other_id")), KASHUBIAN_ENTRY.`as`(
                    "other_entry").ID.`as`("other_entry_id")),
            "KashubianEntryPaged.select/KashubianEntry.meanings" to Triple(MEANING.`as`("meaning"),
                    KASHUBIAN_ENTRY.`as`("entry").ID.eq(MEANING.`as`("meaning").KASHUBIAN_ENTRY_ID),
                    MEANING.`as`("meaning").ID.`as`("meaning_id")),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation" to Triple(TRANSLATION,
                    MEANING.`as`("meaning").ID.eq(TRANSLATION.MEANING_ID), TRANSLATION.ID.`as`(
                    "translation_id"))
    )

    private final val fieldsRelations = mapOf(
            "KashubianEntryPaged.select/KashubianEntry.id" to KASHUBIAN_ENTRY.`as`("entry").ID.`as`("entry_id"),
            "KashubianEntryPaged.select/KashubianEntry.word" to KASHUBIAN_ENTRY.`as`("entry").WORD.`as`("entry_word"),
            "KashubianEntryPaged.select/KashubianEntry.normalizedWord" to KASHUBIAN_ENTRY.`as`("entry").NORMALIZED_WORD.`as`(
                    "entry_normalized_word"),
            "KashubianEntryPaged.select/KashubianEntry.variation" to KASHUBIAN_ENTRY.`as`("entry").VARIATION.`as`("entry_variation"),
            "KashubianEntryPaged.select/KashubianEntry.priority" to KASHUBIAN_ENTRY.`as`("entry").PRIORITY.`as`("entry_priority"),
            "KashubianEntryPaged.select/KashubianEntry.note" to KASHUBIAN_ENTRY.`as`("entry").NOTE.`as`("entry_note"),
            "KashubianEntryPaged.select/KashubianEntry.partOfSpeech" to KASHUBIAN_ENTRY.`as`("entry").PART_OF_SPEECH.`as`(
                    "entry_part_of_speech"),
            "KashubianEntryPaged.select/KashubianEntry.partOfSpeechSubType" to KASHUBIAN_ENTRY.`as`("entry").PART_OF_SPEECH_SUB_TYPE.`as`(
                    "entry_part_of_speech_sub_type"),
            "KashubianEntryPaged.select/KashubianEntry.meaningsCount" to field(selectCount().from(MEANING).where(
                    MEANING.KASHUBIAN_ENTRY_ID.eq(KASHUBIAN_ENTRY.`as`("entry").ID))).`as`("meanings_count"),
            "KashubianEntryPaged.select/KashubianEntry.bases" to field(select(Routines.findBases(KASHUBIAN_ENTRY.`as`(
                    "entry").ID))).`as`(
                    "bases"),
            "KashubianEntryPaged.select/KashubianEntry.derivatives" to field(select(Routines.findDerivatives(
                    KASHUBIAN_ENTRY.`as`("entry").ID))).`as`("derivatives"),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.note" to OTHER.NOTE.`as`("other_note"),
            "KashubianEntryPaged.select/KashubianEntry.others/Other.other/KashubianEntrySimplified.word" to KASHUBIAN_ENTRY.`as`(
                    "other_entry").WORD.`as`("other_entry_word"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.origin" to MEANING.`as`("meaning").ORIGIN.`as`(
                    "meaning_origin"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.definition" to MEANING.`as`("meaning").DEFINITION.`as`(
                    "meaning_definition"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyperonyms" to field(select(Routines.findHyperonyms(
                    MEANING.`as`("meaning").ID))).`as`(
                    "hyperonyms"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyponyms" to field(select(Routines.findHyponyms(
                    MEANING.`as`("meaning").ID))).`as`("hyponyms"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.polish" to TRANSLATION.POLISH.`as`(
                    "translation_polish"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedPolish" to TRANSLATION.NORMALIZED_POLISH.`as`(
                    "translation_normalized_polish"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.english" to TRANSLATION.ENGLISH.`as`(
                    "translation_english"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedEnglish" to TRANSLATION.NORMALIZED_ENGLISH.`as`(
                    "translation_normalized_english"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.german" to TRANSLATION.GERMAN.`as`(
                    "translation_german"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedGerman" to TRANSLATION.NORMALIZED_GERMAN.`as`(
                    "translation_normalized_german"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.ukrainian" to TRANSLATION.UKRAINIAN.`as`(
                    "translation_ukrainian"),
            "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedUkrainian" to TRANSLATION.NORMALIZED_UKRAINIAN.`as`(
                    "translation_normalized_ukrainian")
    )

    @QueryMapping
    fun findAllSearchKashubianEntries(
        @Argument("page") page: Page?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntryPaged {

        val selectedFields: MutableList<SelectFieldOrAsterisk?> = env.selectionSet.fields
            .mapNotNull { fieldsRelations[it.fullyQualifiedName] }
            .toMutableList()

        val selectedJoins = env.selectionSet.fields.mapNotNull { joins[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedFields.add(it.idColumn())
        }

        denseRank().over(orderBy(KASHUBIAN_ENTRY.`as`("entry").ID)).`as`("dense_rank")
            .apply { selectedFields.add(this) }


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
            .addColumnProperty("hyperonyms",
                    ConverterProperty.of(ContextualConverter<PGobject, ArrayNode?> { value, _ ->
                        value?.let { json -> objectMapper.readTree(json.value).let { it as ArrayNode } }
                    }))
            .addColumnProperty("hyponyms",
                    ConverterProperty.of(ContextualConverter<PGobject, ArrayNode?> { value, _ ->
                        value?.let { json -> objectMapper.readTree(json.value).let { it as ArrayNode } }
                    }))
            .newMapper(KashubianEntryGraphQL::class.java)


        return dsl.select(asterisk())
            .from(select(selectedFields)
                .from(KASHUBIAN_ENTRY.`as`("entry"))
                .apply {
                    selectedJoins.forEach {
                        this.join(it.table())
                            .on(it.joinCondition())
                    }
                }
                .orderBy(ordersBy))
            .where(field(name("dense_rank")).between(pageStart, pageEnd))
            .apply { logger.info(sql) }
            .let { KashubianEntryPaged(pageCount, entriesCount, mapper.asList(it)) }
    }

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any { it.fullyQualifiedName == "KashubianEntryPaged.total" || it.fullyQualifiedName == "KashubianEntryPaged.pages" }

    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.table() = this.first
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinCondition() = this.second
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.idColumn() = this.third

}

