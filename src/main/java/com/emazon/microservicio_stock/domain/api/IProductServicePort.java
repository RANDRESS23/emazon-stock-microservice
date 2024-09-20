package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Product;
import org.springframework.data.domain.Page;

public interface IProductServicePort {
    Product saveProduct(Product product);
    void deleteProduct(String name);
    Product getProduct(String name);
    Product getProductById(Long productId);
    Page<Product> getAllProducts(Integer page, Integer size, Boolean ascending, String sortBy);
    Product updateProductQuantity(Long productId, Long extraQuantity);
}
