package tk.aizydorczyk.kashubian.infrastructure

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionSupport {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <ResultType> executeInTransaction(function: () -> ResultType): ResultType {
        return function.invoke()
    }

    @Transactional(readOnly = true)
    fun <ResultType> executeInReadOnlyTransaction(function: () -> ResultType): ResultType {
        return function.invoke()
    }
}