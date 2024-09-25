package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddBrandRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IBrandResponseMapper;
import com.emazon.microservicio_stock.domain.api.IBrandServicePort;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void testGetAllBrandsAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "asc";
        boolean ascending = true;

        // Simular CustomPage<Brand>
        Brand brand = new Brand(1L, "Brand A", "Description A");
        List<Brand> brands = List.of(brand);
        CustomPage<Brand> brandPage = new CustomPage<>();
        brandPage.setPageNumber(page);
        brandPage.setPageSize(size);
        brandPage.setTotalElements(1L);
        brandPage.setTotalPages(1);
        brandPage.setContent(brands);

        // Simular CustomPage<BrandResponse>
        BrandResponse brandResponse = new BrandResponse(1L, "Brand A", "Description A");
        List<BrandResponse> brandResponses = List.of(brandResponse);
        CustomPage<BrandResponse> responsePage = new CustomPage<>();
        responsePage.setPageNumber(page);
        responsePage.setPageSize(size);
        responsePage.setTotalElements(1L);
        responsePage.setTotalPages(1);
        responsePage.setContent(brandResponses);

        // Mockear el comportamiento del servicio y mapper
        when(brandServicePort.getAllBrands(page, size, ascending)).thenReturn(brandPage);
        when(brandResponseMapper.toPageOfBrandResponse(brandPage)).thenReturn(responsePage);

        // Act
        ResponseEntity<CustomPage<BrandResponse>> response = brandRestController.getAllBrands(page, size, sortOrder);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(brandResponse.getBrandId(), response.getBody().getContent().get(0).getBrandId());
        assertEquals(brandResponse.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(brandResponse.getDescription(), response.getBody().getContent().get(0).getDescription());

        // Verificar que el servicio y el mapper fueron llamados correctamente
        verify(brandServicePort).getAllBrands(page, size, ascending);
        verify(brandResponseMapper).toPageOfBrandResponse(brandPage);
    }

    @Test
    void testGetAllBrandsDescending() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "desc";
        boolean ascending = false;

        // Simular CustomPage<Brand>
        Brand brand = new Brand(1L, "Brand B", "Description B");
        List<Brand> brands = List.of(brand);
        CustomPage<Brand> brandPage = new CustomPage<>();
        brandPage.setPageNumber(page);
        brandPage.setPageSize(size);
        brandPage.setTotalElements(1L);
        brandPage.setTotalPages(1);
        brandPage.setContent(brands);

        // Simular CustomPage<BrandResponse>
        BrandResponse brandResponse = new BrandResponse(1L, "Brand B", "Description B");
        List<BrandResponse> brandResponses = List.of(brandResponse);
        CustomPage<BrandResponse> responsePage = new CustomPage<>();
        responsePage.setPageNumber(page);
        responsePage.setPageSize(size);
        responsePage.setTotalElements(1L);
        responsePage.setTotalPages(1);
        responsePage.setContent(brandResponses);

        // Mockear el comportamiento del servicio y mapper
        when(brandServicePort.getAllBrands(page, size, ascending)).thenReturn(brandPage);
        when(brandResponseMapper.toPageOfBrandResponse(brandPage)).thenReturn(responsePage);

        // Act
        ResponseEntity<CustomPage<BrandResponse>> response = brandRestController.getAllBrands(page, size, sortOrder);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(brandResponse.getBrandId(), response.getBody().getContent().get(0).getBrandId());
        assertEquals(brandResponse.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(brandResponse.getDescription(), response.getBody().getContent().get(0).getDescription());

        // Verificar que el servicio y el mapper fueron llamados correctamente
        verify(brandServicePort).getAllBrands(page, size, ascending);
        verify(brandResponseMapper).toPageOfBrandResponse(brandPage);
    }
}