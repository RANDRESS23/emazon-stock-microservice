package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.NotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util.DrivenConstants;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public void saveProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new AlreadyExistsException(DrivenConstants.PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        productRepository.save(productEntityMapper.toEntity(product));
    }

    @Override
    public void deleteProduct(String name) {
        ProductEntity productEntity = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(DrivenConstants.PRODUCT_NOT_FOUND));
        productRepository.delete(productEntity);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name)
                .map(productEntityMapper::toDomainModel);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productEntityMapper::toDomainModel);
    }
}
