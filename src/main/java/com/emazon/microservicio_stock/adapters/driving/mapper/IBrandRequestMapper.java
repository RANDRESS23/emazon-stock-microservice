package com.emazon.microservicio_stock.adapters.driving.mapper;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddBrandRequest;
import com.emazon.microservicio_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = "idBrand", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Brand addRequestToBrand(AddBrandRequest addBrandRequest);
}
