package tk.aizydorczyk.kashubian.crud.query.graphql.base

import org.jooq.QueryPart

data class FieldWithJoins(val field: QueryPart, val joins: List<JoinTableWithCondition>)