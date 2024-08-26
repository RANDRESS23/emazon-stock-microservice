package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Brand;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    void deleteBrand(String name);
    Brand getBrand(String name);
}
