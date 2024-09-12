package com.emazon.microservicio_stock.adapters.driving.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddProductRequest {
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;
    private List<Long> categoriesId;
    private Long brandId;
}
