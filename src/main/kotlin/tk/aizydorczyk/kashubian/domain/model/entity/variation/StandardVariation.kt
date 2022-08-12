package tk.aizydorczyk.kashubian.domain.model.entity.variation

data class StandardVariation(
        val nominative: String? = null,
        val genitive: String? = null,
        val dative: String? = null,
        val accusative: String? = null,
        val instrumental: String? = null,
        val locative: String? = null,
        val vocative: String? = null,
        val nominativePlural: String? = null,
        val genitivePlural: String? = null,
        val dativePlural: String? = null,
        val accusativePlural: String? = null,
        val instrumentalPlural: String? = null,
        val locativePlural: String? = null,
        val vocativePlural: String? = null
)