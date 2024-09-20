package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductDto;
import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductResponse;
import com.emazon.microservicio_stock.adapters.driving.util.DrivingConstants;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {
    ProductResponse toProductResponse(Product product);

    @Mapping(source = DrivingConstants.CATEGORIES, target = DrivingConstants.CATEGORIES, qualifiedByName = DrivingConstants.FORMAT_MAP_TO_CATEGORIES_IDS)
    ProductDto toProductDto(Product product);

    @Named(DrivingConstants.FORMAT_MAP_TO_CATEGORIES_IDS)
    default List<Long> mapToCategoriesIds(List<Category> categories) {
        List<Long> categoriesIds = new ArrayList<>();

        if (!categories.isEmpty()) {
            for (Category category : categories ) {
                categoriesIds.add(category.getCategoryId());
            }
        }

        return categoriesIds;
    }
}
