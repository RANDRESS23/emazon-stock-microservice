package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class BrandAdapterTest {
    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandAdapter brandAdapter;

    @Test
    void saveBrand() {
        Brand brand = new Brand(1L, "Electronics", "Electronics");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronics");

        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.empty());
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(any(BrandEntity.class))).thenReturn(brandEntity);

        // Verificamos que no se lanza ninguna excepción al guardar la marca
        assertDoesNotThrow(() -> brandAdapter.saveBrand(brand));

        // Verificamos que el método save fue llamado una vez
        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    void saveBrandWhenAlreadyExists() {
        Brand brand = new Brand(1L, "Electronics", "Electronics");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronics");

        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.of(brandEntity));

        // Verificamos que se lanza una excepción cuando la marca ya existe
        assertThrows(AlreadyExistsException.class, () -> brandAdapter.saveBrand(brand));

        // Verificamos que el método save nunca fue llamado
        verify(brandRepository, never()).save(any(BrandEntity.class));
    }

    @Test
    void testGetAllBrandsAscending() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        Boolean ascending = true;

        // Crear BrandEntity simulada
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setName("Brand A");
        brandEntity.setDescription("Description A");

        // Crear Brand simulada
        Brand brand = new Brand(1L, "Brand A", "Description A");

        // Simular la paginación de BrandEntity
        Page<BrandEntity> pageOfBrandEntities = new PageImpl<>(List.of(brandEntity), PageRequest.of(page, size), 1);

        // Simular la paginación de Brand
        Page<Brand> pageOfBrands = new PageImpl<>(List.of(brand), PageRequest.of(page, size), 1);

        // Mockear el comportamiento del repositorio y mapper
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(pageOfBrandEntities);
        when(brandEntityMapper.toPageOfBrands(pageOfBrandEntities)).thenReturn(pageOfBrands);

        // Act
        CustomPage<Brand> result = brandAdapter.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getContent().size());
        assertEquals(brand.getBrandId(), result.getContent().get(0).getBrandId());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        assertEquals(brand.getDescription(), result.getContent().get(0).getDescription());

        // Verificar que el repositorio y el mapper fueron llamados correctamente
        verify(brandRepository).findAll(any(Pageable.class));
        verify(brandEntityMapper).toPageOfBrands(pageOfBrandEntities);
    }

    @Test
    void testGetAllBrandsDescending() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        Boolean ascending = false;

        // Crear BrandEntity simulada
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(2L);
        brandEntity.setName("Brand B");
        brandEntity.setDescription("Description B");

        // Crear Brand simulada
        Brand brand = new Brand(2L, "Brand B", "Description B");

        // Simular la paginación de BrandEntity
        Page<BrandEntity> pageOfBrandEntities = new PageImpl<>(List.of(brandEntity), PageRequest.of(page, size), 1);

        // Simular la paginación de Brand
        Page<Brand> pageOfBrands = new PageImpl<>(List.of(brand), PageRequest.of(page, size), 1);

        // Mockear el comportamiento del repositorio y mapper
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(pageOfBrandEntities);
        when(brandEntityMapper.toPageOfBrands(pageOfBrandEntities)).thenReturn(pageOfBrands);

        // Act
        CustomPage<Brand> result = brandAdapter.getAllBrands(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getContent().size());
        assertEquals(brand.getBrandId(), result.getContent().get(0).getBrandId());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        assertEquals(brand.getDescription(), result.getContent().get(0).getDescription());

        // Verificar que el repositorio y el mapper fueron llamados correctamente
        verify(brandRepository).findAll(any(Pageable.class));
        verify(brandEntityMapper).toPageOfBrands(pageOfBrandEntities);
    }
}