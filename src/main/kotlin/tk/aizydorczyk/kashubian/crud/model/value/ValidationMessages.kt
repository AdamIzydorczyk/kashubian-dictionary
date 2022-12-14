package tk.aizydorczyk.kashubian.crud.model.value

class ValidationMessages {
    companion object {
        const val IS_NULL = "IS_NULL"
        const val LENGTH_150_EXCEED = "LENGTH_150_EXCEED"
        const val LENGTH_100_EXCEED = "LENGTH_100_EXCEED"
        const val IS_BLANK = "IS_BLANK"
        const val NOT_AUDIO_EXTENSION_OR_MIME_TYPE = "NOT_AUDIO_EXTENSION_OR_MIME_TYPE"
        const val INCORRECT_NOUN_VARIATION_JSON_FORMAT = "INCORRECT_NOUN_VARIATION_JSON_FORMAT"
        const val INCORRECT_VERB_VARIATION_JSON_FORMAT = "INCORRECT_VERB_VARIATION_JSON_FORMAT"
        const val INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT = "INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT"
        const val INCORRECT_NUMERAL_VARIATION_JSON_FORMAT = "INCORRECT_NUMERAL_VARIATION_JSON_FORMAT"
        const val INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT = "INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT"
        const val INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT =
            "INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT"
        const val INCORRECT_ADVERB_VARIATION_JSON_FORMAT = "INCORRECT_ADVERB_VARIATION_JSON_FORMAT"
        const val VARIATION_IS_NOT_NULL = "VARIATION_IS_NOT_NULL"
        const val ENTRY_NOT_EXISTS = "ENTRY_NOT_EXISTS"
        const val FILE_NOT_EXISTS = "FILE_NOT_EXISTS"
        const val MEANING_NOT_EXISTS = "MEANING_NOT_EXISTS"
        const val PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT = "PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT"
        const val WORD_CHANGED_TO_NON_UNIQUE = "WORD_CHANGED_TO_NON_UNIQUE"
        const val WORD_NOT_UNIQUE = "WORD_NOT_UNIQUE"
        const val NOT_CONTAINS_AT_LEAST_ONE_MEANING = "NOT_CONTAINS_AT_LEAST_ONE_MEANING"
        const val MEANING_OF_UPDATED_ENTRY = "MEANING_OF_UPDATED_ENTRY"
        const val IS_UPDATED_ENTRY = "IS_UPDATED_ENTRY"
        const val IN_UPDATED_ENTRY_BASES = "IN_UPDATED_ENTRY_BASES"
        const val IN_UPDATED_ENTRY_DERIVATIVES = "IN_UPDATED_ENTRY_DERIVATIVES"
        const val HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS =
            "HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS"
        const val IN_UPDATED_ENTRY_MEANINGS_HYPONIMS = "IN_UPDATED_ENTRY_MEANINGS_HYPONIMS"
        const val HYPERONIM_IDS_REPEATED_IN_MEANINGS = "HYPERONIM_IDS_REPEATED_IN_MEANINGS"
        const val NOT_EMAIL_FORMAT = "NOT_EMAIL_FORMAT"
    }
}