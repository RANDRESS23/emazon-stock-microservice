package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {
    ProductEntity toEntity(Product product);
    Product toDomainModel(ProductEntity productEntity);

    default Page<Product> toPageOfProducts(Page<ProductEntity> pageOfProductsEntity) {
        List<Product> dtoList = pageOfProductsEntity.getContent().stream()
                .map(this::toDomainModel)
                .toList();
        return new PageImpl<>(dtoList, pageOfProductsEntity.getPageable(), pageOfProductsEntity.getTotalElements());
    }
}
