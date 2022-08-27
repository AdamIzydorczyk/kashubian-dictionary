package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.domain.MeaningHierarchyElement

data class HyperonymsAndHyponymsDto(val hyperonyms: List<MeaningHierarchyElement>,
    val hyponyms: List<MeaningHierarchyElement>)
