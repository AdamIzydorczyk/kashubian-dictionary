package tk.aizydorczyk.kashubian.domain.model.json

data class VerbVariation(
    val presentVariations: VariationByPersons?,
    val pastVariations: VariationByPersonsAllGenderTypesExtended?,
    val archaicPastVariations: VariationByPersonsAllGenderTypesExtended?,
    val descriptivePresentVariations: VariationByPersonsAllGenderTypes?,
    val pastSecondVariations: VariationByPersonsAllGenderTypesExtended?,
    val prePastFirstVariations: VariationByPersonsAllGenderTypesExtended?,
    val prePastSecondVariations: VariationByPersonsAllGenderTypesExtended?,
    val prePastThirdVariations: VariationByPersonsAllGenderTypesExtended?,
    val prePastImpersonal: String?,
    val futureSimpleVariations: VariationByPersonsAllGenderTypes?,
    val futureComplexVariationsFirst: VariationByPersonsAllGenderTypesExtended?,
    val futureComplexVariationsSecond: VariationByPersonsAllGenderTypesExtended?,
    val imperativeModeVariations: VariationByPersons?,
    val conditionalModeVariations: VariationByPersonsAllGenderTypesExtended?,
    val conditionalModeImpersonal: String?,
    val infinitive: String?,
    val aspectEquivalent: String?,
    val contemporaryAdverbialParticiple: String?,
    val priorAdverbialParticiple: String?,
    val gerundium: String?,
    val gerundiumGrammaticalType: GerundiumGrammaticalType?,
    val gerundiumVariations: VariationByCases?,
    val activeAdjectivalParticiple: String?,
    val activeAdjectivalParticipleVariations: VariationByCasesAllGenderTypes?,
    val passiveAdjectiveParticipleFirst: String?,
    val passiveAdjectiveParticipleVariationsFirst: VariationByCasesAllGenderTypes?,
    val passiveAdjectiveParticipleSecond: String?,
    val passiveAdjectiveParticipleVariationsSecond: VariationByCasesAllGenderTypes?
)

enum class GerundiumGrammaticalType {
    PERFECT, IMPERFECTIVE
}
