package com.ecommerce.challenge.ecommerceProyect.product.dto;

import java.util.List;

public record ProductImportResponse(
        int totalRows,
        int importedRows,
        int failedRows,
        List<String> errors
) {
}