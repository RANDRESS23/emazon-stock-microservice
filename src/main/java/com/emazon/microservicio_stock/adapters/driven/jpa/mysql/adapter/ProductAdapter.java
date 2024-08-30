package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.CategoryNotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.ProductAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.ProductNotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.microservicio_stock.configuration.Constants;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(Constants.PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        productRepository.save(productEntityMapper.toEntity(product));
    }

    @Override
    public void deleteProduct(String name) {
        ProductEntity productEntity = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND));
        productRepository.delete(productEntity);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name)
                .map(productEntityMapper::toDomainModel);
    }
}
