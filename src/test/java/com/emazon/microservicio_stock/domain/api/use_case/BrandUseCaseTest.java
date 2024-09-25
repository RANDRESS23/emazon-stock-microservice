package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.BrandValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void testGetAllBrandsAscending() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        Boolean ascending = true;

        // Simular CustomPage<Brand>
        Brand brand = new Brand(1L, "Brand A", "Description A");
        List<Brand> brands = List.of(brand);
        CustomPage<Brand> brandPage = new CustomPage<>();
        brandPage.setPageNumber(page);
        brandPage.setPageSize(size);
        brandPage.setTotalElements(1L);
        brandPage.setTotalPages(1);
        brandPage.setContent(brands);

        // Mockear el comportamiento del brandPersistencePort
        when(brandPersistencePort.getAllBrands(page, size, ascending)).thenReturn(brandPage);

        // Act
        CustomPage<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(brandPage, result);  // Verificar que el resultado es el mismo que el simulado
        assertEquals(1, result.getContent().size());
        assertEquals(brand.getBrandId(), result.getContent().get(0).getBrandId());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        assertEquals(brand.getDescription(), result.getContent().get(0).getDescription());

        // Verificar que el puerto fue llamado correctamente
        verify(brandPersistencePort).getAllBrands(page, size, ascending);
    }

    @Test
    void testGetAllBrandsDescending() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        Boolean ascending = false;

        // Simular CustomPage<Brand>
        Brand brand = new Brand(2L, "Brand B", "Description B");
        List<Brand> brands = List.of(brand);
        CustomPage<Brand> brandPage = new CustomPage<>();
        brandPage.setPageNumber(page);
        brandPage.setPageSize(size);
        brandPage.setTotalElements(1L);
        brandPage.setTotalPages(1);
        brandPage.setContent(brands);

        // Mockear el comportamiento del brandPersistencePort
        when(brandPersistencePort.getAllBrands(page, size, ascending)).thenReturn(brandPage);

        // Act
        CustomPage<Brand> result = brandUseCase.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(brandPage, result);  // Verificar que el resultado es el mismo que el simulado
        assertEquals(1, result.getContent().size());
        assertEquals(brand.getBrandId(), result.getContent().get(0).getBrandId());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        assertEquals(brand.getDescription(), result.getContent().get(0).getDescription());

        // Verificar que el puerto fue llamado correctamente
        verify(brandPersistencePort).getAllBrands(page, size, ascending);
    }
}