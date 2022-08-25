package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.domain.MeaningHierarchyElement

data class MeaningHierarchyDto(
    val derivativeMeaningsIds: List<MeaningHierarchyElement>,
    val baseMeaningsIds: List<MeaningHierarchyElement>,
    val hyperonymsIds: List<MeaningHierarchyElement>,
    val hyponymsIds: List<MeaningHierarchyElement>)
