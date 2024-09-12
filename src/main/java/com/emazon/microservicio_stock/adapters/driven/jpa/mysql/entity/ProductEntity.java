package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util.DrivenConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = DrivenConstants.PRODUCT_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = DrivenConstants.FIELD_NAME_NOT_BLANK_MESSAGE)
    private String name;

    @NotBlank(message = DrivenConstants.FIELD_DESCRIPTION_NOT_BLANK_MESSAGE)
    private String description;

    private Long quantity;

    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = DrivenConstants.PRODUCT_CATEGORY_TABLE_NAME,
            joinColumns = @JoinColumn(name = DrivenConstants.COLUMN_PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = DrivenConstants.COLUMN_CATEGORY_ID)
    )
    private List<CategoryEntity> categories;

    @ManyToOne
    @JoinColumn(name = DrivenConstants.COLUMN_BRAND_ID)
    private BrandEntity brand;
}
