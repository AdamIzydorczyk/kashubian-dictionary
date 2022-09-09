package tk.aizydorczyk.kashubian.crud.model.entitysearch

import tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry.SearchKashubianEntry

data class SearchMeaning(
    val id: Long,
    val translation: Set<SearchTranslation> = emptySet(),
    val definition: String?,
    val origin: String?,
    val proverbs: Set<SearchProverb> = emptySet(),
    val phrasalVerbs: Set<SearchPhrasalVerb> = emptySet(),
    val quotes: Set<SearchQuote> = emptySet(),
    val examples: Set<SearchExample> = emptySet(),
    val synonyms: Set<SearchSynonym> = emptySet(),
    val antonyms: Set<SearchAntonym> = emptySet(),

    val kashubianEntry: SearchKashubianEntry
)
