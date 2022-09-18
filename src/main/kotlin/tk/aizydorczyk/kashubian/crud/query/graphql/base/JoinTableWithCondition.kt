package tk.aizydorczyk.kashubian.crud.query.graphql.base

import org.jooq.Condition
import org.jooq.impl.TableImpl

data class JoinTableWithCondition(val joinTable: TableImpl<*>, val joinCondition: Condition)