package com.emazon.microservicio_stock.adapters.driving.dto.response;

import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
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
    private List<Category> categories;
    private Brand brand;
}
