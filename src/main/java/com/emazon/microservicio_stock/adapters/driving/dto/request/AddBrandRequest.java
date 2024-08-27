package com.emazon.microservicio_stock.adapters.driving.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddBrandRequest {
    private final String name;
    private final String description;
}
