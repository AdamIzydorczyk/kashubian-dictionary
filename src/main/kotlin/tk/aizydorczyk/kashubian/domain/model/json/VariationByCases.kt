package tk.aizydorczyk.kashubian.domain.model.json

data class VariationByCases(
    val nominative: String?,
    val genitive: String?,
    val dative: String?,
    val accusative: String?,
    val instrumental: String?,
    val locative: String?,
    val vocative: String?,
    val nominativePlural: String?,
    val genitivePlural: String?,
    val dativePlural: String?,
    val accusativePlural: String?,
    val instrumentalPlural: String?,
    val locativePlural: String?,
    val vocativePlural: String?
)