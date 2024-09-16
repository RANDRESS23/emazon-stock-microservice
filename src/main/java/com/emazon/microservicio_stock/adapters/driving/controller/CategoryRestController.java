package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddCategoryRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryResponseMapper;
import com.emazon.microservicio_stock.adapters.driving.util.DrivingConstants;
import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.model.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = DrivingConstants.TAG_CATEGORY_NAME, description = DrivingConstants.TAG_CATEGORY_DESCRIPTION)
public class CategoryRestController {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Operation(summary = DrivingConstants.SAVE_CATEGORY_SUMMARY, description = DrivingConstants.SAVE_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.SAVE_CATEGORY_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.SAVE_CATEGORY_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_409, description = DrivingConstants.SAVE_CATEGORY_RESPONSE_409_DESCRIPTION, content = @Content)
    })
    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody AddCategoryRequest request) {
        Category category = categoryRequestMapper.addRequestToCategory(request);
        Category categorySaved = categoryServicePort.saveCategory(category);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(categorySaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = DrivingConstants.GET_CATEGORY_SUMMARY, description = DrivingConstants.GET_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.GET_CATEGORY_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.GET_CATEGORY_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_404, description = DrivingConstants.GET_CATEGORY_RESPONSE_404_DESCRIPTION, content = @Content)
    })
    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String name) {
        Category category = categoryServicePort.getCategory(name);
        CategoryResponse response = categoryResponseMapper.toCategoryResponse(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = DrivingConstants.GET_ALL_CATEGORIES_PAGINATED_SUMMARY, description = DrivingConstants.GET_ALL_CATEGORIES_PAGINATED_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_200, description = DrivingConstants.GET_ALL_CATEGORIES_PAGINATED_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.GET_ALL_CATEGORIES_PAGINATED_RESPONSE_400_DESCRIPTION, content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_PAGE_PARAM) int page,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SIZE_PARAM) int size,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SORT_PARAM) String sortOrder) {
        boolean ascending = DrivingConstants.DEFAULT_SORT_PARAM.equalsIgnoreCase(sortOrder);
        Page<Category> categoryPage = categoryServicePort.getAllCategories(page, size, ascending);
        Page<CategoryResponse> responsePage = categoryPage.map(categoryResponseMapper::toCategoryResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Operation(summary = DrivingConstants.DELETE_CATEGORY_SUMMARY, description = DrivingConstants.DELETE_CATEGORY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.DELETE_CATEGORY_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.DELETE_CATEGORY_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_404, description = DrivingConstants.DELETE_CATEGORY_RESPONSE_404_DESCRIPTION, content = @Content)
    })
    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryServicePort.deleteCategory(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
