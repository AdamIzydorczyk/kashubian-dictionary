package tk.aizydorczyk.kashubian.crud.query.graphql

import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntriesPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningGraphQL
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria
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

    @QueryMapping
    fun findKashubianEntry(@Argument("id") id: Long,
        env: DataFetchingEnvironment): KashubianEntryGraphQL? {
        val selectedFields = env.selectionSet.fields
        return oneKashubianEntryFinder.findOneKashubianEntry(selectedFields, id)
    }

    @QueryMapping
    fun findAllKashubianEntries(
        @Argument("page") page: PageCriteria?,
        @Argument("where") where: KashubianEntryCriteriaExpression?,
        env: DataFetchingEnvironment): KashubianEntriesPaged {
        val selectedFields = env.selectionSet.fields
        return allKashubianEntriesFinder.findAllKashubianEntries(where, selectedFields, page)
    }

    @QueryMapping
    fun findMeaning(@Argument("id") id: Long,
        env: DataFetchingEnvironment): MeaningGraphQL? {
        val selectedFields = env.selectionSet.fields
        return oneMeaningFinder.findOneMeaning(selectedFields, id)
    }

    @QueryMapping
    fun findAllMeanings(
        @Argument("page") page: PageCriteria?,
        @Argument("where") where: MeaningsCriteriaExpression?,
        env: DataFetchingEnvironment): MeaningsPaged {
        val selectedFields = env.selectionSet.fields
        return allMeaningsFinder.findAllMeanings(where, selectedFields, page)
    }
}