package com.emazon.microservicio_stock.configuration.feign;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddSupplyRequest;
import com.emazon.microservicio_stock.configuration.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = Constants.TRANSACTION_SERVICE_NAME, url = Constants.TRANSACTION_SERVICE_URL, configuration = FeignClientInterceptor.class)
public interface ITransactionFeignClient {
    @PostMapping
    ResponseEntity<Void> addSupply(@RequestBody AddSupplyRequest request);
}
