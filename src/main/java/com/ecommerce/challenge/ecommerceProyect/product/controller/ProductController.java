package com.ecommerce.challenge.ecommerceProyect.product.controller;

import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductImportResponse;
import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductResponse;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.ProductCsvImportServiceImpl;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductCsvImportServiceImpl productCsvImportService;

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String query) {
        return productService.search(query);
    }

    @PostMapping("/import")
    public ProductImportResponse importProducts(@RequestParam("file") MultipartFile file) {
        return productCsvImportService.importProducts(file);
    }
}