package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddProductRequest;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = "idProduct", ignore = true)
    @Mapping(source = "idBrand", target = "brand.idBrand")
    @Mapping(target = "brand.name", constant = "name")
    @Mapping(target = "brand.description", constant = "description")
    @Mapping(source = "idCategories", target = "categories", qualifiedByName = "mapToCategories")
    Product addRequestToProduct(AddProductRequest addProductRequest);

    @Named("mapToCategories")
    default List<Category> mapToCategories(List<Long> idCategories) {
        List<Category> categories = new ArrayList<>();

        if (!idCategories.isEmpty()) {
            for (Long categoryId : idCategories ) {
                categories.add(new Category(categoryId, "category: ", "category: "));
            }
        }

        return categories;
    }
}
