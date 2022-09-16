package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.SelectedField
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SelectFieldOrAsterisk
import org.jooq.SortField
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.entitysearch.Tables
import tk.aizydorczyk.kashubian.crud.model.graphql.JoinTableWithCondition
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntriesPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.KashubianEntryGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.KashubianEntryQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS
import kotlin.reflect.full.declaredMemberProperties

@Component
class AllKashubianEntriesFinder(private val dsl: DSLContext) : AllFinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = KashubianEntryGraphQLMapper()

    fun findAll(where: KashubianEntryCriteriaExpression?,
        selectedFields: MutableList<SelectedField>,
        page: PageCriteria?): KashubianEntriesPaged {
        val wheresWithJoins = prepareWheresWithJoins("entry",
                where,
                KashubianEntryCriteriaExpression::class.declaredMemberProperties,
                CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
        )

        val wheres = wheresWithJoins.map { it.condition() }
        val whereJoins = wheresWithJoins.map { it.joins() }
            .flatten()
            .distinct()

        val selectedColumns: MutableList<SelectFieldOrAsterisk?> = selectColumns(selectedFields,
                FIND_ALL_FIELD_TO_COLUMN_RELATIONS)
        selectedColumns.add(KashubianEntryQueryRelations.entryId())

        val selectedJoins = selectedFields
            .mapNotNull { FIND_ALL_FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val entriesCount = countEntriesIfSelected(selectedFields, whereJoins, wheres)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (entriesCount + limit - 1) / limit

        val ordersBy: List<SortField<*>> = orderByColumns(selectedFields,
                FIND_ALL_FIELD_TO_COLUMN_RELATIONS)

        return dsl.select(selectedColumns)
            .from(KashubianEntryQueryRelations.entryTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(KashubianEntryQueryRelations.entryTable().ID.`in`(
                    DSL.select(KashubianEntryQueryRelations.entryTable().ID)
                        .from(KashubianEntryQueryRelations.entryTable())
                        .apply {
                            whereJoins.forEach {
                                leftJoin(it.joinTable).on(it.joinCondition)
                            }
                            wheres.forEach {
                                where(it)
                            }
                        }
                        .offset(pageStart)
                        .limit(limit)))
            .orderBy(ordersBy)
            .apply { logger.info("Select query: $sql") }
            .let { KashubianEntriesPaged(pageCount, entriesCount, mapper.map(it.fetch())) }
    }

    private fun countEntriesIfSelected(
        selectedFields: MutableList<SelectedField>,
        whereJoins: List<JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields, "KashubianEntryPaged.total", "KashubianEntryPaged.pages")) {
            true -> dsl.select(DSL.count())
                .from(Tables.KASHUBIAN_ENTRY.`as`("entry"))
                .apply {
                    whereJoins.forEach {
                        leftJoin(it.joinTable).on(it.joinCondition)
                    }

                    wheres.forEach {
                        where(it)
                    }
                    logger.info("Select query: $sql")
                }.fetchOne(0, Int::class.java) ?: 0

            false -> 0
        }
}