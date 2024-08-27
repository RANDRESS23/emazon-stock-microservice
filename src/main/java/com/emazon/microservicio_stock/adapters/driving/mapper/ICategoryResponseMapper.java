package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryResponse;
import com.emazon.microservicio_stock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponse(Category category);
}
