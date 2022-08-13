package tk.aizydorczyk.kashubian.domain.model.json

data class AdjectiveVariation(
    val adjectiveVariation: VariationByCasesAllGenderTypes?,
    val inAssemblies: String?,
    val base: String?,
    val comparative: String?,
    val superlative: String?,
)
