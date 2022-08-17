package tk.aizydorczyk.kashubian.crud.model.json

data class VariationByPersonsAllGenderTypesExtended(
    val firstPersonMasculine: String?,
    val firstPersonFeminine: String?,
    val firstPersonNeuter: String?,
    val firstPersonPluralMasculineFirst: String?,
    val firstPersonNonMasculineFirst: String?,
    val firstPersonPluralSecondMasculine: String?,
    val firstPersonPluralSecondNonMasculine: String?,
    val secondPersonMasculine: String?,
    val secondPersonFeminine: String?,
    val secondPersonNeuter: String?,
    val secondPersonPluralMasculineSecond: String?,
    val secondPersonNonMasculineSecond: String?,
    val secondPersonPluralMasculineFirst: String?,
    val secondPersonNonMasculineFirst: String?,
    val secondPersonPluralMasculineThird: String?,
    val secondPersonNonMasculineThird: String?,
    val thirdPersonMasculine: String?,
    val thirdPersonFeminine: String?,
    val thirdPersonNeuter: String?,
    val thirdPersonPluralMasculine: String?,
    val thirdPersonNonMasculine: String?
)