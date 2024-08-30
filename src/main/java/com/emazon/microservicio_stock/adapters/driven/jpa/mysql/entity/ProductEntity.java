package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotBlank(message = "Field 'name' cannot be null")
    private String name;

    @NotBlank(message = "Field 'description' cannot be null")
    private String description;

    private Long quantity;

    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private List<CategoryEntity> categories;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private BrandEntity brand;
}
