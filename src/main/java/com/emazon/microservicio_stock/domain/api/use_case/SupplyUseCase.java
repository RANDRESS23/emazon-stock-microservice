package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.ISupplyServicePort;
import com.emazon.microservicio_stock.domain.spi.ITransactionPersistencePort;

public class SupplyUseCase implements ISupplyServicePort {
    private final ITransactionPersistencePort transactionPersistencePort;

    public SupplyUseCase(ITransactionPersistencePort transactionPersistencePort) {
        this.transactionPersistencePort = transactionPersistencePort;
    }

    @Override
    public void saveSupply(Long productId, Long extraQuantity) {
        transactionPersistencePort.saveSupply(productId, extraQuantity);
    }
}
