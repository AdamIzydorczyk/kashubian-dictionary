package tk.aizydorczyk.kashubian.domain.model.dto

import java.math.BigInteger

data class MeaningHierarchyDto(
    val derivativeMeaningsIds: List<BigInteger>,
    val baseMeaningsIds: List<BigInteger>,
    val hyperonymsIds: List<BigInteger>,
    val hyponymsIds: List<BigInteger>)
