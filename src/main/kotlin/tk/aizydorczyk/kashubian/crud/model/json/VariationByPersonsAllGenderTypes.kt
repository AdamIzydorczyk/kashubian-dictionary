package tk.aizydorczyk.kashubian.crud.model.json

data class VariationByPersonsAllGenderTypes(
    val firstPersonSingularMasculine: String?,
    val firstPersonSingularFeminine: String?,
    val firstPersonSingularNeuter: String?,
    val firstPersonPluralMasculineFirst: String?,
    val firstPersonPluralNonMasculineFirst: String?,
    val firstPersonPluralMasculineSecond: String?,
    val firstPersonPluralNonMasculineSecond: String?,
    val secondPersonSingularMasculine: String?,
    val secondPersonSingularFeminine: String?,
    val secondPersonSingularNeuter: String?,
    val secondPersonPluralMasculineSecond: String?,
    val secondPersonPluralNonMasculineSecond: String?,
    val thirdPersonSingularMasculine: String?,
    val thirdPersonSingularFeminine: String?,
    val thirdPersonSingular: String?,
    val secondPersonPluralMasculineFirst: String?,
    val secondPersonPluralNonMasculineFirst: String?,
    val thirdPersonPluralMasculine: String?,
    val thirdPersonPluralNonMasculine: String?
)