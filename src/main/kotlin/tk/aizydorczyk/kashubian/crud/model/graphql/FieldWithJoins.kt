package tk.aizydorczyk.kashubian.crud.model.graphql

import org.jooq.QueryPart

data class FieldWithJoins(val field: QueryPart, val joins: List<JoinTableWithCondition>)