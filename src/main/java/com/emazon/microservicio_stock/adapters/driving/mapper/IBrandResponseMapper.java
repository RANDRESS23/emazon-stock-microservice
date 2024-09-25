package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandResponse;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);

    default CustomPage<BrandResponse> toPageOfBrandResponse(CustomPage<Brand> pageBrands) {
        List<BrandResponse> dtoList = pageBrands.getContent().stream()
                .map(this::toBrandResponse)
                .toList();
        CustomPage<BrandResponse> customPage = new CustomPage<>();
        customPage.setPageNumber(pageBrands.getPageNumber());
        customPage.setPageSize(pageBrands.getPageSize());
        customPage.setTotalElements(pageBrands.getTotalElements());
        customPage.setTotalPages(pageBrands.getTotalPages());
        customPage.setContent(dtoList);

        return customPage;
    }
}
