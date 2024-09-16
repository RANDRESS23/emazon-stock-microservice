package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    Optional<ProductEntity> findById(Long productId);
    Page<ProductEntity> findAll(Pageable pageable);
}
