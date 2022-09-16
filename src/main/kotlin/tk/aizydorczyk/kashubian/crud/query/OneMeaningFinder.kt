package tk.aizydorczyk.kashubian.crud.query

import graphql.schema.SelectedField
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.graphql.MeaningGraphQL

@Component
class OneMeaningFinder {
    fun findOne(selectedFields: List<SelectedField>, id: Long): MeaningGraphQL? {
        TODO("Not yet implemented")
    }
}