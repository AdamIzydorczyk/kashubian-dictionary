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
import tk.aizydorczyk.kashubian.crud.model.graphql.JoinTableWithCondition
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.mapper.MeaningGraphQLMapper
import tk.aizydorczyk.kashubian.crud.query.MeaningQueryRelations.CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
import tk.aizydorczyk.kashubian.crud.query.MeaningQueryRelations.FIND_ALL_FIELD_TO_COLUMN_RELATIONS
import tk.aizydorczyk.kashubian.crud.query.MeaningQueryRelations.FIND_ALL_FIELD_TO_JOIN_RELATIONS
import kotlin.reflect.full.declaredMemberProperties

@Component
class AllMeaningsFinder(private val dsl: DSLContext) : AllFinderBase() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val mapper = MeaningGraphQLMapper()

    fun findAll(where: MeaningsCriteriaExpression?,
        selectedFields: List<SelectedField>,
        page: PageCriteria?): MeaningsPaged {
        val wheresWithJoins = prepareWheresWithJoins("meaning",
                where,
                MeaningsCriteriaExpression::class.declaredMemberProperties,
                CRITERIA_TO_COLUMN_RELATIONS_WITH_JOIN
        )

        val wheres = wheresWithJoins.map { it.condition() }
        val whereJoins = wheresWithJoins.map { it.joins() }
            .flatten()
            .distinct()

        val selectedColumns: MutableList<SelectFieldOrAsterisk?> = selectColumns(selectedFields,
                FIND_ALL_FIELD_TO_COLUMN_RELATIONS)
        selectedColumns.add(MeaningQueryRelations.meaningId())

        val selectedJoins = selectedFields
            .mapNotNull { FIND_ALL_FIELD_TO_JOIN_RELATIONS[it.fullyQualifiedName] }

        selectedJoins.forEach {
            selectedColumns.add(it.idColumn())
        }

        val entriesCount = countMeaningsIfSelected(selectedFields, whereJoins, wheres)

        val limit = page?.limit ?: 100
        val start = page?.start ?: 0
        val pageStart = start * limit
        val pageCount: Int = (entriesCount + limit - 1) / limit

        val ordersBy: List<SortField<*>> = orderByColumns(selectedFields,
                FIND_ALL_FIELD_TO_COLUMN_RELATIONS)

        return dsl.select(selectedColumns)
            .from(MeaningQueryRelations.meaningTable())
            .apply {
                selectedJoins.forEach {
                    leftJoin(it.joinTable()).on(it.joinCondition())
                }
            }
            .where(MeaningQueryRelations.meaningTable().ID.`in`(
                    DSL.select(MeaningQueryRelations.meaningTable().ID)
                        .from(MeaningQueryRelations.meaningTable())
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
            .let { MeaningsPaged(pageCount, entriesCount, mapper.map(it.fetch())) }
    }

    private fun countMeaningsIfSelected(
        selectedFields: List<SelectedField>,
        whereJoins: List<JoinTableWithCondition>,
        wheres: List<Condition?>) =
        when (isContainsPaginationFields(selectedFields, "MeaningsPaged.total", "MeaningsPaged.pages")) {
            true -> dsl.select(DSL.count())
                .from(MeaningQueryRelations.meaningTable())
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
