package tk.aizydorczyk.kashubian.domain.model.json

data class NumeralVariation(
    val numeralVariation: VariationByCasesAllGenderTypes?,
    val inAssemblies: String?,
    val base: String?,
    val comparative: String?,
    val superlative: String?
)
