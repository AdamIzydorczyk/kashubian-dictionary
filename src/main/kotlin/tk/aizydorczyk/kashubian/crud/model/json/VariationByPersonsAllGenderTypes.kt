package tk.aizydorczyk.kashubian.crud.model.json

data class VariationByPersonsAllGenderTypes(
    val firstPersonMasculine: String?,
    val firstPersonFeminine: String?,
    val firstPersonNeuter: String?,
    val firstPersonPluralMasculineFirst: String?,
    val firstPersonNonMasculineFirst: String?,
    val firstPersonPluralMasculineSecond: String?,
    val firstPersonNonMasculineSecond: String?,
    val secondPersonMasculine: String?,
    val secondPersonFeminine: String?,
    val secondPersonNeuter: String?,
    val secondPersonPluralMasculineSecond: String?,
    val secondPersonNonMasculineSecond: String?,
    val thirdPersonMasculine: String?,
    val thirdPersonFeminine: String?,
    val thirdPersonSingular: String?,
    val secondPersonPluralMasculineFirst: String?,
    val secondPersonNonMasculineFirst: String?,
    val thirdPersonPluralMasculine: String?,
    val thirdPersonNonMasculine: String?
)