package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);
    void deleteBrand(String name);
    Optional<Brand> getBrand(String name);
    CustomPage<Brand> getAllBrands(Integer page, Integer size, Boolean ascending);
    Optional<Brand> getBrandById(Long idBrand);
}
