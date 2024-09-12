package com.emazon.microservicio_stock.adapters.driving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandDto {
    private final Long brandId;
    private final String name;
}
