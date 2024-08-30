package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
    void deleteProduct(String name);
    Product getProduct(String name);
}
