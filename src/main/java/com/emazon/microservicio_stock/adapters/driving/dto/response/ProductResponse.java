package com.emazon.microservicio_stock.adapters.driving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ProductResponse {
    private final Long idProduct;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;
    private List<CategoryDto> categories;
    private BrandDto brand;
}
