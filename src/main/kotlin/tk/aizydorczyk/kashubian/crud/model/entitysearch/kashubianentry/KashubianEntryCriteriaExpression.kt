package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

data class KashubianEntryCriteriaExpression(val id: NumericCriteria?, val word: StringCriteria?)
data class NumericCriteria(val EQ: String?)
data class StringCriteria(val EQ: String?, val LIKE: String?, val _LIKE: String?)
