package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Brand;

import java.util.Optional;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    void deleteBrand(String name);
    Optional<Brand> getBrand(String name);
}
