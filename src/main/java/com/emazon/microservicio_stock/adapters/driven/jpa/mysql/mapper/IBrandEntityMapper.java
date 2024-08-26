package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity toEntity(Brand brand);

    @Mapping(source = "idBrand", target = "idBrand")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand toDomainModel(BrandEntity brandEntity);
}
