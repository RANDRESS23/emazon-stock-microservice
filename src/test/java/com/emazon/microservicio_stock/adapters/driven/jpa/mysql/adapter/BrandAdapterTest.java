package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.emazon.microservicio_stock.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertThrows(BrandAlreadyExistsException.class, () -> brandAdapter.saveBrand(brand));

        // Verificamos que el método save nunca fue llamado
        verify(brandRepository, never()).save(any(BrandEntity.class));
    }
}