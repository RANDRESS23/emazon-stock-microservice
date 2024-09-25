package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    Brand toDomainModel(BrandEntity brandEntity);

    default Page<Brand> toPageOfBrands(Page<BrandEntity> pageOfBrandsEntity) {
        List<Brand> dtoList = pageOfBrandsEntity.getContent().stream()
                .map(this::toDomainModel)
                .toList();
        return new PageImpl<>(dtoList, pageOfBrandsEntity.getPageable(), pageOfBrandsEntity.getTotalElements());
    }
}
