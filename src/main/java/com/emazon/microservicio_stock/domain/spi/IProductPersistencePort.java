package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Product;

import java.util.Optional;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    void deleteProduct(String name);
    Optional<Product> getProductByName(String name);
}
