package tk.aizydorczyk.kashubian.crud.model.mapper

import org.jooq.Record
import org.jooq.Result

interface GraphQLMapper<out GraphQLModel> {
    fun map(results: Result<Record>): List<GraphQLModel>
}
