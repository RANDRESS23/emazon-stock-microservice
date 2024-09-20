package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddSupplyRequest;
import com.emazon.microservicio_stock.configuration.feign.ITransactionFeignClient;
import com.emazon.microservicio_stock.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionAdapter implements ITransactionPersistencePort {
    private final ITransactionFeignClient transactionFeignClient;

    @Override
    public void saveSupply(Long productId, Long extraQuantity) {
        AddSupplyRequest request = new AddSupplyRequest(productId, extraQuantity);
        transactionFeignClient.addSupply(request);
    }
}
