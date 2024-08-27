package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddBrandRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandResponseMapper;
import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.model.Brand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @PostMapping
    public ResponseEntity<BrandResponse> addBrand(@Valid @RequestBody AddBrandRequest request) {
        Brand brand = brandRequestMapper.addRequestToBrand(request);
        brandServicePort.saveBrand(brand);
        BrandResponse response = brandResponseMapper.toBrandResponse(brand);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable("name") String name) {
        Brand brand = brandServicePort.getBrand(name);
        BrandResponse response = brandResponseMapper.toBrandResponse(brand);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("name") String name) {
        brandServicePort.deleteBrand(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
