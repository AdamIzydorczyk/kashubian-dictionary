package tk.aizydorczyk.kashubian.domain.model.json

data class VariationByPersons(
    val firstPersonSingular: String?,
    val firstPersonPluralFirst: String?,
    val firstPersonPluralSecond: String?,
    val secondPersonSingular: String?,
    val secondPersonPluralFirst: String?,
    val secondPersonPluralSecond: String?,
    val thirdPersonSingular: String?,
    val thirdPersonPlural: String?
)