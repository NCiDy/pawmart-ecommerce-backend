package com.pawmart.DTO.Product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {
    private Long id;

    private Long categoryId;

    private String categoryName;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String brand;

    private BigDecimal weight;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
