package tk.aizydorczyk.kashubian.crud.model.json

data class VariationByCases(
    val nominative: String?,
    val nominativePlural: String?,
    val genitive: String?,
    val genitivePlural: String?,
    val dative: String?,
    val dativePlural: String?,
    val accusative: String?,
    val accusativePlural: String?,
    val instrumental: String?,
    val instrumentalPlural: String?,
    val locative: String?,
    val locativePlural: String?,
    val vocative: String?,
    val vocativePlural: String?
)