package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddBrandRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandResponseMapper;
import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandRestControllerTest {
    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandRestController brandRestController;

    @Test
    void addBrand_shouldReturnCreatedBrand() {
        // Arrange
        AddBrandRequest request = new AddBrandRequest("Brand A", "Description of Brand A");
        Brand brand = new Brand(null, "Brand A", "Description of Brand A");
        Brand brandSaved = new Brand(1L, "Brand A", "Description of Brand A");
        BrandResponse brandResponse = new BrandResponse(1L, "Brand A", "Description of Brand A");

        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);
        when(brandServicePort.saveBrand(brand)).thenReturn(brandSaved);
        when(brandResponseMapper.toBrandResponse(brandSaved)).thenReturn(brandResponse);

        // Act
        ResponseEntity<BrandResponse> response = brandRestController.addBrand(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(brandResponse, response.getBody());
        verify(brandRequestMapper, times(1)).addRequestToBrand(request);
        verify(brandServicePort, times(1)).saveBrand(brand);
        verify(brandResponseMapper, times(1)).toBrandResponse(brandSaved);
    }

    @Test
    void getBrand_shouldReturnBrandResponse() {
        // Arrange
        String name = "Brand A";
        Brand brand = new Brand(1L, "Brand A", "Description of Brand A");
        BrandResponse brandResponse = new BrandResponse(1L, "Brand A", "Description of Brand A");

        when(brandServicePort.getBrand(name)).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);

        // Act
        ResponseEntity<BrandResponse> response = brandRestController.getBrand(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandResponse, response.getBody());
        verify(brandServicePort, times(1)).getBrand(name);
        verify(brandResponseMapper, times(1)).toBrandResponse(brand);
    }

    @Test
    void getAllBrands_shouldReturnPageOfBrandResponses() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "asc";
        Brand brand = new Brand(1L, "Brand A", "Description of Brand A");
        BrandResponse brandResponse = new BrandResponse(1L, "Brand A", "Description of Brand A");

        Page<Brand> brandPage = new PageImpl<>(List.of(brand));
        Page<BrandResponse> responsePage = new PageImpl<>(List.of(brandResponse));

        when(brandServicePort.getAllBrands(page, size, true)).thenReturn(brandPage);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);

        // Act
        ResponseEntity<Page<BrandResponse>> response = brandRestController.getAllBrands(page, size, sortOrder);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsePage, response.getBody());
        verify(brandServicePort, times(1)).getAllBrands(page, size, true);
        verify(brandResponseMapper, times(1)).toBrandResponse(brand);
    }

    @Test
    void deleteBrand_shouldReturnNoContent() {
        // Arrange
        String name = "Brand A";

        // Act
        ResponseEntity<Void> response = brandRestController.deleteBrand(name);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(brandServicePort, times(1)).deleteBrand(name);
    }
}