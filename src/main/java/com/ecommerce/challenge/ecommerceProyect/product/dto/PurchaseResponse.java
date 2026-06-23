package com.ecommerce.challenge.ecommerceProyect.product.dto;

import com.ecommerce.challenge.ecommerceProyect.product.entity.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(
        Long id,
        Long productId,
        String productName,
        String sku,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal total,
        PurchaseStatus status,
        LocalDateTime createdAt
) {
}