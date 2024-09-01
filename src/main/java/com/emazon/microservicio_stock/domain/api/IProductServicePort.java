package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Product;
import org.springframework.data.domain.Page;

public interface IProductServicePort {
    void saveProduct(Product product);
    void deleteProduct(String name);
    Product getProduct(String name);
    Page<Product> getAllProducts(Integer page, Integer size, Boolean ascending, String sortBy);
}
