package com.emazon.microservicio_stock.domain.spi;

public interface ITransactionPersistencePort {
    void saveSupply(Long productId, Long extraQuantity);
}
