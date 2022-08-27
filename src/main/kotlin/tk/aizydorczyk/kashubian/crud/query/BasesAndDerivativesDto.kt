package tk.aizydorczyk.kashubian.crud.query

import tk.aizydorczyk.kashubian.crud.domain.EntryHierarchyElement

data class BasesAndDerivativesDto(
    val bases: List<EntryHierarchyElement>,
    val derivatives: List<EntryHierarchyElement>)
