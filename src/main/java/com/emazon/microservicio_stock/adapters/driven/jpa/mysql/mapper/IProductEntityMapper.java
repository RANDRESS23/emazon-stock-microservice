package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    ProductEntity toEntity(Product product);

    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    Product toDomainModel(ProductEntity productEntity);
}
