package tk.aizydorczyk.kashubian;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@TestComponent
public class TransactionSupport {
    @Transactional
    public <ResultType> ResultType executeInTransaction(Supplier<ResultType> supplier) {
        return supplier.get();
    }
}
