package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.DataFetchingEnvironment
import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SelectFieldOrAsterisk
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.select
import org.jooq.impl.DSL.selectCount
import org.jooq.impl.TableImpl
import org.jooq.impl.UpdatableRecordImpl
import org.simpleflatmapper.jooq.SelectQueryMapper
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
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.Page

@Controller
class KashubianEntryQuery(
    private val dsl: DSLContext,
    private val mapper: SelectQueryMapper<KashubianEntryGraphQL>) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @QueryMapping
    fun findAllSearchKashubianEntries(
        @Argument("page") page: Page?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntryPaged {
        val selectedFields = env.selectionSet.fields

        val selectedColumns: MutableList<SelectFieldOrAsterisk?> = selectFields(env)
        val selectedJoins = selectedFields
            .mapNotNull { FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val entriesCount = countEntriesIfSelected(selectedFields)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (entriesCount + limit - 1) / limit

        return dsl.select(selectedColumns)
            .from(KASHUBIAN_ENTRY.`as`("entry"))
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.table()).on(it.joinCondition())
                }
            }
            .where(KASHUBIAN_ENTRY.`as`("entry").ID.`in`(
                    select(KASHUBIAN_ENTRY.ID)
                        .from(KASHUBIAN_ENTRY)
                        .offset(pageStart)
                        .limit(limit)))
            .orderBy(orderByColumns(selectedFields))
            .apply { logger.info(sql) }
            .let { KashubianEntryPaged(pageCount, entriesCount, mapper.asList(it)) }
    }

    private fun countEntriesIfSelected(selectedFields: MutableList<SelectedField>) =
        when (isContainsPaginationFields(selectedFields)) {
            true -> dsl.fetchCount(KASHUBIAN_ENTRY)
            false -> 0
        }

    private fun orderByColumns(selectedFields: MutableList<SelectedField>) =
        selectedFields.filter { it.arguments.isNotEmpty() }.map {
            when (it.arguments["orderBy"]) {
                "ASC" -> FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName]!!.asc()
                else -> FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName]!!.desc()
            }
        }

    private fun selectFields(env: DataFetchingEnvironment): MutableList<SelectFieldOrAsterisk?> =
        env.selectionSet.fields
            .mapNotNull { FIELD_TO_COLUMN_RELATIONS[it.fullyQualifiedName] }
            .toMutableList()

    private fun isContainsPaginationFields(fields: MutableList<SelectedField>) =
        fields.any {
            it.fullyQualifiedName == "KashubianEntryPaged.total"
                    || it.fullyQualifiedName == "KashubianEntryPaged.pages"
        }

    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.table() = this.first
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.joinCondition() = this.second
    fun Triple<TableImpl<out UpdatableRecordImpl<*>>, Condition, Field<Long>>.idColumn() = this.third

    companion object Relations {
        private val FIELD_TO_JOIN_RELATIONS = mapOf(
                "KashubianEntryPaged.select/KashubianEntry.others" to
                        Triple(OTHER,
                                KASHUBIAN_ENTRY.`as`("entry").ID.eq(OTHER.KASHUBIAN_ENTRY_ID),
                                OTHER.ID.`as`("other_id")),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.other" to
                        Triple(KASHUBIAN_ENTRY.`as`("other_entry"),
                                KASHUBIAN_ENTRY.`as`("other_entry").ID.eq(OTHER.OTHER_ID.`as`("other_id")),
                                KASHUBIAN_ENTRY.`as`("other_entry").ID.`as`("other_entry_id")),
                "KashubianEntryPaged.select/KashubianEntry.meanings" to
                        Triple(MEANING.`as`("meaning"),
                                KASHUBIAN_ENTRY.`as`("entry").ID.eq(MEANING.`as`("meaning").KASHUBIAN_ENTRY_ID),
                                MEANING.`as`("meaning").ID.`as`("meaning_id")),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation" to
                        Triple(TRANSLATION,
                                MEANING.`as`("meaning").ID.eq(TRANSLATION.MEANING_ID),
                                TRANSLATION.ID.`as`("translation_id"))
        )

        private val FIELD_TO_COLUMN_RELATIONS = mapOf(
                "KashubianEntryPaged.select/KashubianEntry.id" to
                        KASHUBIAN_ENTRY.`as`("entry").ID.`as`("entry_id"),
                "KashubianEntryPaged.select/KashubianEntry.word" to
                        KASHUBIAN_ENTRY.`as`("entry").WORD.`as`("entry_word"),
                "KashubianEntryPaged.select/KashubianEntry.normalizedWord" to
                        KASHUBIAN_ENTRY.`as`("entry").NORMALIZED_WORD.`as`("entry_normalized_word"),
                "KashubianEntryPaged.select/KashubianEntry.variation" to
                        KASHUBIAN_ENTRY.`as`("entry").VARIATION.`as`("entry_variation"),
                "KashubianEntryPaged.select/KashubianEntry.priority" to
                        KASHUBIAN_ENTRY.`as`("entry").PRIORITY.`as`("entry_priority"),
                "KashubianEntryPaged.select/KashubianEntry.note" to
                        KASHUBIAN_ENTRY.`as`("entry").NOTE.`as`("entry_note"),
                "KashubianEntryPaged.select/KashubianEntry.partOfSpeech" to
                        KASHUBIAN_ENTRY.`as`("entry").PART_OF_SPEECH.`as`("entry_part_of_speech"),
                "KashubianEntryPaged.select/KashubianEntry.partOfSpeechSubType" to
                        KASHUBIAN_ENTRY.`as`("entry").PART_OF_SPEECH_SUB_TYPE.`as`("entry_part_of_speech_sub_type"),
                "KashubianEntryPaged.select/KashubianEntry.meaningsCount" to
                        field(selectCount().from(MEANING)
                            .where(MEANING.KASHUBIAN_ENTRY_ID.eq(KASHUBIAN_ENTRY.`as`("entry").ID))).`as`("meanings_count"),
                "KashubianEntryPaged.select/KashubianEntry.bases" to
                        field(select(Routines.findBases(KASHUBIAN_ENTRY.`as`("entry").ID))).`as`("bases"),
                "KashubianEntryPaged.select/KashubianEntry.derivatives" to
                        field(select(Routines.findDerivatives(KASHUBIAN_ENTRY.`as`("entry").ID))).`as`("derivatives"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.note" to
                        OTHER.NOTE.`as`("other_note"),
                "KashubianEntryPaged.select/KashubianEntry.others/Other.other/KashubianEntrySimplified.word" to
                        KASHUBIAN_ENTRY.`as`("other_entry").WORD.`as`("other_entry_word"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.origin" to
                        MEANING.`as`("meaning").ORIGIN.`as`("meaning_origin"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.definition" to
                        MEANING.`as`("meaning").DEFINITION.`as`("meaning_definition"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyperonyms" to
                        field(select(Routines.findHyperonyms(MEANING.`as`("meaning").ID))).`as`("hyperonyms"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.hyponyms" to
                        field(select(Routines.findHyponyms(MEANING.`as`("meaning").ID))).`as`("hyponyms"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.polish" to
                        TRANSLATION.POLISH.`as`("translation_polish"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedPolish" to
                        TRANSLATION.NORMALIZED_POLISH.`as`("translation_normalized_polish"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.english" to
                        TRANSLATION.ENGLISH.`as`("translation_english"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedEnglish" to
                        TRANSLATION.NORMALIZED_ENGLISH.`as`("translation_normalized_english"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.german" to
                        TRANSLATION.GERMAN.`as`("translation_german"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedGerman" to
                        TRANSLATION.NORMALIZED_GERMAN.`as`("translation_normalized_german"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.ukrainian" to
                        TRANSLATION.UKRAINIAN.`as`("translation_ukrainian"),
                "KashubianEntryPaged.select/KashubianEntry.meanings/Meaning.translation/Translation.normalizedUkrainian" to
                        TRANSLATION.NORMALIZED_UKRAINIAN.`as`("translation_normalized_ukrainian")
        )
    }
}
