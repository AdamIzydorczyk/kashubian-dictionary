package tk.aizydorczyk.kashubian.crud.model.graphql.model

import com.fasterxml.jackson.databind.JsonNode
import tk.aizydorczyk.kashubian.crud.query.graphql.base.GraphQLModel

data class MeaningGraphQL(
    val id: Long,
    val definition: String?,
    val origin: String?,
    var hyperonym: MeaningSimplifiedGraphQL? = null,
    val hyperonyms: JsonNode?,
    val hyponyms: JsonNode?,
    var translation: TranslationGraphQL? = null,
    val proverbs: MutableSet<ProverbGraphQL> = mutableSetOf(),
    val quotes: MutableSet<QuoteGraphQL> = mutableSetOf(),
    val examples: MutableSet<ExampleGraphQL> = mutableSetOf(),
    val phrasalVerbs: MutableSet<PhrasalVerbGraphQL> = mutableSetOf(),
    val antonyms: MutableSet<AntonymGraphQL> = mutableSetOf(),
    val synonyms: MutableSet<SynonymGraphQL> = mutableSetOf()
) : GraphQLModel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MeaningGraphQL) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}