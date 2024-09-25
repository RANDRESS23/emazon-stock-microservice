package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;

public interface IProductServicePort {
    Product saveProduct(Product product);
    void deleteProduct(String name);
    Product getProduct(String name);
    Product getProductById(Long productId);
    CustomPage<Product> getAllProducts(Integer page, Integer size, Boolean ascending, String sortBy);
    Product updateProductQuantity(Long productId, Long extraQuantity);
}
