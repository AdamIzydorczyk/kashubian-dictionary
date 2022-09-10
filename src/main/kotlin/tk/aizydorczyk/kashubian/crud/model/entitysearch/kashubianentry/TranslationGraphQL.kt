package tk.aizydorczyk.kashubian.crud.model.entitysearch.kashubianentry

import org.simpleflatmapper.map.annotation.Column

class TranslationGraphQL {
    @get:Column("translation_id")
    var id: Long? = null

    @get:Column("translation_polish")
    var polish: String? = null

    @get:Column("translation_normalized_polish")
    var normalizedPolish: String? = null

    @get:Column("translation_english")
    var english: String? = null

    @get:Column("translation_normalized_english")
    var normalizedEnglish: String? = null

    @get:Column("translation_ukrainian")
    var ukrainian: String? = null

    @get:Column("translation_normalized_ukrainian")
    var normalizedUkrainian: String? = null

    @get:Column("translation_german")
    var german: String? = null

    @get:Column("translation_normalized_german")
    var normalizedGerman: String? = null
}