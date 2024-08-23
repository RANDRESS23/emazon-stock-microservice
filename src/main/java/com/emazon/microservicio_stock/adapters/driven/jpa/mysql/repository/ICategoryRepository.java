package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    void deleteByName(String name);
    Page<CategoryEntity> findAll(Pageable pageable);
}
