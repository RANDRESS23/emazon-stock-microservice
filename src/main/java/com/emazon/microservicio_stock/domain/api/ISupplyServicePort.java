package com.emazon.microservicio_stock.domain.api;

public interface ISupplyServicePort {
    void saveSupply(Long productId, Long extraQuantity);
}
