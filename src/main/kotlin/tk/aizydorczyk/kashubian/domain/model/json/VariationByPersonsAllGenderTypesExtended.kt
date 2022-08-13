package tk.aizydorczyk.kashubian.domain.model.json

data class VariationByPersonsAllGenderTypesExtended(
    val firstPersonSingularMasculine: String?,
    val firstPersonSingularFeminine: String?,
    val firstPersonSingularNeuter: String?,
    val firstPersonPluralMasculineFirst: String?,
    val firstPersonPluralNonMasculineFirst: String?,
    val firstPersonPluralSecondMasculine: String?,
    val firstPersonPluralSecondNonMasculine: String?,
    val secondPersonSingularMasculine: String?,
    val secondPersonSingularFeminine: String?,
    val secondPersonSingularNeuter: String?,
    val secondPersonPluralMasculineSecond: String?,
    val secondPersonPluralNonMasculineSecond: String?,
    val thirdPersonSingularMasculine: String?,
    val thirdPersonSingularFeminine: String?,
    val thirdPersonSingularNeuter: String?,
    val waMasculineFirst: String?,
    val waMasculineSecond: String?,
    val waNonMasculineFirst: String?,
    val waNonMasculineSecond: String?,
    val thirdPersonPluralMasculine: String?,
    val thirdPersonPluralNonMasculine: String?
)