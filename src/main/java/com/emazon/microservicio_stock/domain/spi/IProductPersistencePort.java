package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductPersistencePort {
    void saveProduct(Product product);
    void deleteProduct(String name);
    Optional<Product> getProductByName(String name);
    Page<Product> getAllProducts(Pageable pageable);
}
