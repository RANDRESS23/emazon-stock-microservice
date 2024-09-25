package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;

public interface IBrandServicePort {
    Brand saveBrand(Brand brand);
    void deleteBrand(String name);
    Brand getBrand(String name);
    CustomPage<Brand> getAllBrands(Integer page, Integer size, Boolean ascending);
}
