package tk.aizydorczyk.kashubian.crud.query

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningHierarchyDto


@Component
class MeaningHierarchyFinder(val repository: KashubianEntryRepository) {

    @Transactional(readOnly = true)
    fun find(meaningId: Long): MeaningHierarchyDto {
        val derivativeMeaningsIds = repository.findDerivativeMeaningsIds(meaningId)
        val baseMeaningsIds = repository.findBaseMeaningsIds(meaningId)
        val hyperonymsIds = repository.findHyperonymsIds(meaningId)
        val hyponymsIds = repository.findHyponymsIds(meaningId)
        return MeaningHierarchyDto(
                derivativeMeaningsIds = derivativeMeaningsIds,
                baseMeaningsIds = baseMeaningsIds,
                hyperonymsIds = hyperonymsIds,
                hyponymsIds = hyponymsIds)
    }

}