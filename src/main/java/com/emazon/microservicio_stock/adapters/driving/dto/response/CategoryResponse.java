package com.emazon.microservicio_stock.adapters.driving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {
    private final Long categoryId;
    private final String name;
    private final String description;
}
