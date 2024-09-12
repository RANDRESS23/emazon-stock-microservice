package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util.DrivenConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = DrivenConstants.CATEGORY_TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = DrivenConstants.FIELD_NAME_NOT_BLANK_MESSAGE)
    private String name;

    @NotBlank(message = DrivenConstants.FIELD_DESCRIPTION_NOT_BLANK_MESSAGE)
    private String description;
}
