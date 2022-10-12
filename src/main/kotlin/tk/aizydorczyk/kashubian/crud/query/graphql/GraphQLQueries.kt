package tk.aizydorczyk.kashubian.crud.query.graphql

import graphql.schema.DataFetchingEnvironment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.criteria.PageCriteria
import tk.aizydorczyk.kashubian.crud.model.graphql.model.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.model.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.page.KashubianEntriesPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.page.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.AllKashubianEntriesFinder
import tk.aizydorczyk.kashubian.crud.query.graphql.entry.OneKashubianEntryFinder
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.AllMeaningsFinder
import tk.aizydorczyk.kashubian.crud.query.graphql.meaning.OneMeaningFinder

@Controller
class GraphQLQueries(
    val oneKashubianEntryFinder: OneKashubianEntryFinder,
    val allKashubianEntriesFinder: AllKashubianEntriesFinder,
    val oneMeaningFinder: OneMeaningFinder,
    val allMeaningsFinder: AllMeaningsFinder) {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @QueryMapping
    fun findKashubianEntry(@Argument("id") id: Long,
        env: DataFetchingEnvironment): KashubianEntryGraphQL? {
        val selectedFields = env.selectionSet.fields
        logger.info("Entry searching by id: $id with fields: $selectedFields")
        return oneKashubianEntryFinder.findOneKashubianEntry(selectedFields, id)
    }

    @QueryMapping
    fun findAllKashubianEntries(
        @Argument("page") page: PageCriteria?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntriesPaged {
        val selectedFields = env.selectionSet.fields
        logger.info("Entries searching by criteria: $where with page: $page and fields: $selectedFields")
        return allKashubianEntriesFinder.findAllKashubianEntries(where, selectedFields, page)
    }

    @QueryMapping
    fun findMeaning(@Argument("id") id: Long,
        env: DataFetchingEnvironment): MeaningGraphQL? {
        val selectedFields = env.selectionSet.fields
        logger.info("Meaning searching by id: $id with fields: $selectedFields")
        return oneMeaningFinder.findOneMeaning(selectedFields, id)
    }

    @QueryMapping
    fun findAllMeanings(
        @Argument("page") page: PageCriteria?,
        @Argument("where") where: MeaningsCriteriaExpression?,
        env: DataFetchingEnvironment): MeaningsPaged {
        val selectedFields = env.selectionSet.fields
        logger.info("Meanings searching by criteria: $where with page: $page and fields: $selectedFields")
        return allMeaningsFinder.findAllMeanings(where, selectedFields, page)
    }
}