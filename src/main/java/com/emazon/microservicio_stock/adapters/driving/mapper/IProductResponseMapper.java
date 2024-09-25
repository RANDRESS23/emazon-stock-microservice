package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductDto;
import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductResponse;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {
    ProductResponse toProductResponse(Product product);
    ProductDto toProductDto(Product product);

    default CustomPage<ProductResponse> toPageOfProductResponse(CustomPage<Product> pageProducts) {
        List<ProductResponse> dtoList = pageProducts.getContent().stream()
                .map(this::toProductResponse)
                .toList();
        CustomPage<ProductResponse> customPage = new CustomPage<>();
        customPage.setPageNumber(pageProducts.getPageNumber());
        customPage.setPageSize(pageProducts.getPageSize());
        customPage.setTotalElements(pageProducts.getTotalElements());
        customPage.setTotalPages(pageProducts.getTotalPages());
        customPage.setContent(dtoList);

        return customPage;
    }
}
