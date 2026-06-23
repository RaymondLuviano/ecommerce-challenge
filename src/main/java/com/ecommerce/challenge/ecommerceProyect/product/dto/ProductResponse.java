package com.ecommerce.challenge.ecommerceProyect.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        String description,
        String category,
        BigDecimal price,
        Integer stock,
        BigDecimal weightKg,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}