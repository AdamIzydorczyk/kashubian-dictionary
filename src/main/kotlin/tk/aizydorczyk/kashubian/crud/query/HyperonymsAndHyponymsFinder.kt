package tk.aizydorczyk.kashubian.crud.query

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.crud.model.dto.HyperonymsAndHyponymsDto


@Component
class HyperonymsAndHyponymsFinder(val repository: KashubianEntryRepository) {

    @Transactional(readOnly = true)
    fun find(meaningId: Long): HyperonymsAndHyponymsDto = HyperonymsAndHyponymsDto(
            hyperonyms = repository.findHyperonyms(meaningId),
            hyponyms = repository.findHyponyms(meaningId))

}