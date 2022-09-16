package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.SelectedField
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsCriteriaExpression
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningsPaged
import tk.aizydorczyk.kashubian.crud.model.graphql.PageCriteria

@Component
class AllMeaningsFinder {
    fun findAll(where: MeaningsCriteriaExpression?,
        selectedFields: List<SelectedField>,
        page: PageCriteria?): MeaningsPaged {
        TODO("Not yet implemented")
    }
}