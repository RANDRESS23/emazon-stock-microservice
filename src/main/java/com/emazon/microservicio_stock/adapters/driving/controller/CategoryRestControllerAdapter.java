package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddCategoryRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryResponseMapper;
import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.model.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody AddCategoryRequest request) {
        Category category = categoryRequestMapper.addRequestToCategory(request);
        categoryServicePort.saveCategory(category);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("name") String name) {
        Category category = categoryServicePort.getCategory(name);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        boolean ascending = "asc".equalsIgnoreCase(sortOrder);
        Page<Category> categoryPage = categoryServicePort.getAllCategories(page, size, ascending);
        Page<CategoryResponse> responsePage = categoryPage.map(categoryResponseMapper::toCategoryResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("name") String name) {
        categoryServicePort.deleteCategory(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
