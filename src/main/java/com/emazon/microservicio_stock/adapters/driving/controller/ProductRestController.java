package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddProductRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductResponseMapper;
import com.emazon.microservicio_stock.adapters.driving.util.DrivingConstants;
import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;

    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        Product product = productRequestMapper.addRequestToProduct(request);
        productServicePort.saveProduct(product);
        ProductResponse response = productResponseMapper.toProductResponse(product);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllCategories(
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_PAGE_PARAM) int page,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SIZE_PARAM) int size,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SORT_PARAM) String sortOrder,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SORT_BY_PARAM) String sortBy) {
        boolean ascending = DrivingConstants.DEFAULT_SORT_PARAM.equalsIgnoreCase(sortOrder);
        Page<Product> productPage = productServicePort.getAllProducts(page, size, ascending, sortBy);
        Page<ProductResponse> responsePage = productPage.map(productResponseMapper::toProductResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String name) {
        Product product = productServicePort.getProduct(name);
        ProductResponse response = productResponseMapper.toProductResponse(product);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productServicePort.deleteProduct(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
