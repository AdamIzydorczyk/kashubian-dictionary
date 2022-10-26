package tk.aizydorczyk.kashubian.crud.extension

import org.apache.commons.lang3.StringUtils
import org.jooq.Condition
import org.jooq.QueryPart
import org.jooq.impl.TableImpl
import tk.aizydorczyk.kashubian.crud.query.graphql.base.FieldWithJoins
import tk.aizydorczyk.kashubian.crud.query.graphql.base.JoinTableWithCondition

fun String.normalize(): String = this.let(StringUtils::stripAccents)
    .replace("\\s".toRegex(), "")
    .replace("[^\\p{L} ]".toRegex(), "")
    .replace("ß", "b")
    .lowercase()

fun Pair<String, FieldWithJoins>.fieldPath() = this.first
fun Pair<String, FieldWithJoins>.fieldWithJoins() = this.second

infix fun TableImpl<*>.on(that: Condition): JoinTableWithCondition =
    JoinTableWithCondition(this, that)

infix fun QueryPart.joinedBy(that: List<JoinTableWithCondition>): FieldWithJoins =
    FieldWithJoins(this, that)