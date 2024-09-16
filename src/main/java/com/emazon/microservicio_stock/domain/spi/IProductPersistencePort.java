package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductPersistencePort {
    Product saveProduct(Product product);
    void deleteProduct(String name);
    Product updateProductQuantity(Long productId, Long extraQuantity);
    Optional<Product> getProductByName(String name);
    Optional<Product> getProductById(Long productId);
    Page<Product> getAllProducts(Pageable pageable);
}
