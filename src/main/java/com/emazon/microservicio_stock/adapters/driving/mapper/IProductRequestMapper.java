package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddProductRequest;
import com.emazon.microservicio_stock.adapters.driving.util.DrivingConstants;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = DrivingConstants.PRODUCT_ID, ignore = true)
    @Mapping(source = DrivingConstants.BRAND_ID, target = DrivingConstants.BRAND_BRAND_ID)
    @Mapping(target = DrivingConstants.BRAND_BRAND_NAME, constant = DrivingConstants.FIELD_NAME)
    @Mapping(target = DrivingConstants.BRAND_BRAND_DESCRIPTION, constant = DrivingConstants.FIELD_DESCRIPTION)
    @Mapping(source = DrivingConstants.CATEGORIES_ID, target = DrivingConstants.CATEGORIES, qualifiedByName = DrivingConstants.FORMAT_MAP_TO_CATEGORIES)
    Product addRequestToProduct(AddProductRequest addProductRequest);

    @Named(DrivingConstants.FORMAT_MAP_TO_CATEGORIES)
    default List<Category> mapToCategories(List<Long> categoriesId) {
        List<Category> categories = new ArrayList<>();

        if (!categoriesId.isEmpty()) {
            for (Long categoryId : categoriesId ) {
                categories.add(new Category(categoryId, "", ""));
            }
        }

        return categories;
    }
}
