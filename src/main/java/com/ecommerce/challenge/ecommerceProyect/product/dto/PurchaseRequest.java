package com.ecommerce.challenge.ecommerceProyect.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(

        @NotNull
        Long productId,

        @NotNull
        @Min(1)
        Integer quantity
) {
}