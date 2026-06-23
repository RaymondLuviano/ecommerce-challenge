package com.ecommerce.challenge.ecommerceProyect.product.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank
        @Size(max = 80)
        String sku,

        String description,

        @NotBlank
        @Size(max = 80)
        String category,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stock,

        @DecimalMin(value = "0.0")
        BigDecimal weightKg
) {
}