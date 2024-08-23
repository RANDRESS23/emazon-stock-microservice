package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity toEntity(Category category);

    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Category toDomainModel(CategoryEntity categoryEntity);
}
