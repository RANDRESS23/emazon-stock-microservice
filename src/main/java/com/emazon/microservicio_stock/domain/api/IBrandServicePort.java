package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Brand;
import org.springframework.data.domain.Page;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    void deleteBrand(String name);
    Brand getBrand(String name);
    Page<Brand> getAllBrands(Integer page, Integer size, Boolean ascending);
}
