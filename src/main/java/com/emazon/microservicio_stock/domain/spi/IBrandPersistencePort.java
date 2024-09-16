package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);
    void deleteBrand(String name);
    Optional<Brand> getBrand(String name);
    Page<Brand> getAllBrands(Pageable pageable);
    Optional<Brand> getBrandById(Long idBrand);
}
