package com.ecommerce.challenge.ecommerceProyect.product.service.impl;

import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductImportResponse;
import com.ecommerce.challenge.ecommerceProyect.product.entity.ProductEntity;
import com.ecommerce.challenge.ecommerceProyect.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCsvImportServiceImpl {

    private final ProductRepository productRepository;

    @Transactional
    public ProductImportResponse importProducts(MultipartFile file) {
        List<String> errors = new ArrayList<>();
        int totalRows = 0;
        int importedRows = 0;

        try (
                InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                CSVParser parser = CSVFormat.DEFAULT
                        .builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .build()
                        .parse(reader)
        ) {
            for (CSVRecord record : parser) {
                totalRows++;

                try {
                    ProductEntity product = buildProduct(record);

                    if (productRepository.existsBySku(product.getSku())) {
                        errors.add("Row " + totalRows + ": SKU already exists: " + product.getSku());
                        continue;
                    }

                    productRepository.save(product);
                    importedRows++;

                } catch (Exception ex) {
                    errors.add("Row " + totalRows + ": " + ex.getMessage());
                }
            }

        } catch (Exception ex) {
            errors.add("File error: " + ex.getMessage());
        }

        return new ProductImportResponse(
                totalRows,
                importedRows,
                totalRows - importedRows,
                errors
        );
    }

    private ProductEntity buildProduct(CSVRecord record) {
        String name = cleanText(record.get("name"));
        String sku = cleanText(record.get("sku"));
        String description = cleanText(record.get("description"));
        String category = cleanText(record.get("category"));
        BigDecimal price = parsePrice(record.get("price"));
        Integer stock = parseStock(record.get("stock"));
        BigDecimal weightKg = parseDecimal(record.get("weight_kg"), "weight_kg");

        validateRequired(name, "name");
        validateRequired(sku, "sku");
        validateRequired(category, "category");

        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setSku(sku);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(stock);
        product.setWeightKg(weightKg);

        return product;
    }

    private String cleanText(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replaceAll("<[^>]*>", "")
                .trim();
    }

    private void validateRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    private BigDecimal parsePrice(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("price is required");
        }

        String cleanValue = value.replace("$", "").replace(",", "").trim();

        try {
            BigDecimal price = new BigDecimal(cleanValue);

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("price must be greater than zero");
            }

            return price;

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("invalid price: " + value);
        }
    }

    private Integer parseStock(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("stock is required");
        }

        try {
            Integer stock = Integer.valueOf(value.trim());

            if (stock < 0) {
                throw new IllegalArgumentException("stock cannot be negative");
            }

            return stock;

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("invalid stock: " + value);
        }
    }

    private BigDecimal parseDecimal(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return new BigDecimal(value.replace("$", "").replace(",", "").trim());

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("invalid " + fieldName + ": " + value);
        }
    }
}