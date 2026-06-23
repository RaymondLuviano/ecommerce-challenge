package com.ecommerce.challenge.ecommerceProyect.product.controller;

import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.PurchaseRequest;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.ProductCsvImportServiceImpl;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.ProductServiceImpl;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.PurchaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductServiceImpl productService;
    private final ProductCsvImportServiceImpl productCsvImportService;
    private final PurchaseServiceImpl purchaseService;

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String query, Model model) {
        var products = query == null || query.isBlank()
                ? productService.findAll()
                : productService.search(query);

        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("productId", null);
        model.addAttribute("product", new ProductRequest(
                "",
                "",
                "",
                "",
                BigDecimal.ZERO,
                0,
                BigDecimal.ZERO
        ));
        return "product-form";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute ProductRequest product) {
        productService.create(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {
        var product = productService.findById(id);

        model.addAttribute("productId", id);
        model.addAttribute("product", new ProductRequest(
                product.name(),
                product.sku(),
                product.description(),
                product.category(),
                product.price(),
                product.stock(),
                product.weightKg()
        ));

        return "product-form";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductRequest product
    ) {
        productService.update(id, product);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @PostMapping("/products/import")
    public String importProducts(
            @RequestParam("file") MultipartFile file,
            Model model
    ) {
        var result = productCsvImportService.importProducts(file);

        model.addAttribute("products", productService.findAll());
        model.addAttribute("importResult", result);

        return "products";
    }

    @PostMapping("/products/{id}/purchase")
    public String purchaseProduct(
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {
        purchaseService.purchase(new PurchaseRequest(id, quantity));
        return "redirect:/products";
    }
}
