package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddProductRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductResponseMapper;
import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestControllerAdapter {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        Product product = productRequestMapper.addRequestToProduct(request);
        productServicePort.saveProduct(product);
        ProductResponse response = productResponseMapper.toProductResponse(product);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "name") String sortBy) {
        boolean ascending = "asc".equalsIgnoreCase(sortOrder);
        Page<Product> productPage = productServicePort.getAllProducts(page, size, ascending, sortBy);
        Page<ProductResponse> responsePage = productPage.map(productResponseMapper::toProductResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("name") String name) {
        Product product = productServicePort.getProduct(name);
        ProductResponse response = productResponseMapper.toProductResponse(product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("name") String name) {
        productServicePort.deleteProduct(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
