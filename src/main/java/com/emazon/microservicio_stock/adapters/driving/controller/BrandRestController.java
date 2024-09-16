package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddBrandRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandResponseMapper;
import com.emazon.microservicio_stock.adapters.driving.util.DrivingConstants;
import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.model.Brand;
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
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
@Tag(name = DrivingConstants.TAG_BRAND_NAME, description = DrivingConstants.TAG_BRAND_DESCRIPTION)
public class BrandRestController {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = DrivingConstants.SAVE_BRAND_SUMMARY, description = DrivingConstants.SAVE_BRAND_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.SAVE_BRAND_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.SAVE_BRAND_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_409, description = DrivingConstants.SAVE_BRAND_RESPONSE_409_DESCRIPTION, content = @Content)
    })
    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<BrandResponse> addBrand(@Valid @RequestBody AddBrandRequest request) {
        Brand brand = brandRequestMapper.addRequestToBrand(request);
        Brand brandSaved = brandServicePort.saveBrand(brand);
        BrandResponse response = brandResponseMapper.toBrandResponse(brandSaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = DrivingConstants.GET_BRAND_SUMMARY, description = DrivingConstants.GET_BRAND_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.GET_BRAND_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.GET_BRAND_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_404, description = DrivingConstants.GET_BRAND_RESPONSE_404_DESCRIPTION, content = @Content)
    })
    @GetMapping("/{name}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable String name) {
        Brand brand = brandServicePort.getBrand(name);
        BrandResponse response = brandResponseMapper.toBrandResponse(brand);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = DrivingConstants.GET_ALL_BRANDS_PAGINATED_SUMMARY, description = DrivingConstants.GET_ALL_BRANDS_PAGINATED_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_200, description = DrivingConstants.GET_ALL_BRANDS_PAGINATED_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.GET_ALL_BRANDS_PAGINATED_RESPONSE_400_DESCRIPTION, content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_PAGE_PARAM) int page,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SIZE_PARAM) int size,
            @RequestParam(defaultValue = DrivingConstants.DEFAULT_SORT_PARAM) String sortOrder) {
        boolean ascending = DrivingConstants.DEFAULT_SORT_PARAM.equalsIgnoreCase(sortOrder);
        Page<Brand> brandPage = brandServicePort.getAllBrands(page, size, ascending);
        Page<BrandResponse> responsePage = brandPage.map(brandResponseMapper::toBrandResponse);

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Operation(summary = DrivingConstants.DELETE_BRAND_SUMMARY, description = DrivingConstants.DELETE_BRAND_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_201, description = DrivingConstants.DELETE_BRAND_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_400, description = DrivingConstants.DELETE_BRAND_RESPONSE_400_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = DrivingConstants.RESPONSE_CODE_404, description = DrivingConstants.DELETE_BRAND_RESPONSE_404_DESCRIPTION, content = @Content)
    })
    @PreAuthorize(DrivingConstants.HAS_ROLE_ADMIN)
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String name) {
        brandServicePort.deleteBrand(name);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
