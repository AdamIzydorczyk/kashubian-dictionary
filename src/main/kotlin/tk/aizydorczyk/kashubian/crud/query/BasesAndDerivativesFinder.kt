package tk.aizydorczyk.kashubian.crud.query

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository

@Component
class BasesAndDerivativesFinder(val repository: KashubianEntryRepository) {
    @Transactional(readOnly = true)
    fun find(entryId: Long) = BasesAndDerivativesDto(
            bases = repository.findBases(entryId),
            derivatives = repository.findDerivatives(entryId))

}
