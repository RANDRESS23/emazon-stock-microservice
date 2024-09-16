package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.BrandValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private BrandValidation brandValidation;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    void testCreateBrandSuccessfully() {
        // Arrange
        Brand brand = new Brand(null, "Electrónica", "Marca para electrónicos");

        when(brandPersistencePort.getBrand(brand.getName())).thenReturn(Optional.empty());

        // Act
        brandUseCase.saveBrand(brand);

        // Assert
        verify(brandPersistencePort).saveBrand(brand);
    }

    @Test
    void testCreateBrandWithExistingName() {
        // Arrange
        Brand brand = new Brand(null, "Electrónica", "Marca para electrónicos");

        when(brandPersistencePort.getBrand(brand.getName())).thenReturn(Optional.of(brand));

        // Act & Assert
        AlreadyExistsFieldException exception = assertThrows(
                AlreadyExistsFieldException.class,
                () -> brandUseCase.saveBrand(brand)
        );

        assertEquals(DomainConstants.BRAND_ALREADY_EXISTS_MESSAGE, exception.getMessage());
        verify(brandPersistencePort, never()).saveBrand(brand);
    }

    @Test
    void testDeleteBrandSuccessfully() {
        // Arrange
        String brandName = "Electrónica";
        Brand brand = new Brand(null, brandName, "Marca para electrónicos");

        when(brandPersistencePort.getBrand(brandName)).thenReturn(Optional.of(brand));

        // Act
        brandUseCase.deleteBrand(brandName);

        // Assert
        verify(brandPersistencePort).deleteBrand(brandName);
    }

    @Test
    void testDeleteBrandNotFound() {
        // Arrange
        String brandName = "Electrónica";

        when(brandPersistencePort.getBrand(brandName)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> brandUseCase.deleteBrand(brandName)
        );

        assertEquals(DomainConstants.BRAND_NOT_FOUND, exception.getMessage());
        verify(brandPersistencePort, never()).deleteBrand(brandName);
    }

    @Test
    void testGetAllBrandsSuccessfullyAscending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = true;

        Brand brand1 = new Brand(null, "A", "Description A");
        Brand brand2 = new Brand(null, "B", "Description B");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                2
        );

        when(brandPersistencePort.getAllBrands(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("A", result.getContent().get(0).getName());
        assertEquals("B", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllBrandsSuccessfullyDescending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = false;

        Brand brand1 = new Brand(null, "B", "Description B");
        Brand brand2 = new Brand(null, "A", "Description A");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").descending()),
                2
        );

        when(brandPersistencePort.getAllBrands(PageRequest.of(page, size, Sort.by("name").descending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("B", result.getContent().get(0).getName());
        assertEquals("A", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllBrandsPagination() {
        // Arrange
        int page = 1;
        int size = 5;
        boolean ascending = true;

        Brand brand1 = new Brand(null, "C", "Description C");
        Brand brand2 = new Brand(null, "D", "Description D");

        Page<Brand> brandPage = new PageImpl<>(
                Arrays.asList(brand1, brand2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                10
        );

        when(brandPersistencePort.getAllBrands(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(brandPage);

        // Act
        Page<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(brand1, result.getContent().get(0));
        assertEquals(brand2, result.getContent().get(1));
    }
}