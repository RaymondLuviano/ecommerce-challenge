package com.ecommerce.challenge.ecommerceProyect.product.controller;

import com.ecommerce.challenge.ecommerceProyect.product.dto.PurchaseRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.PurchaseResponse;
import com.ecommerce.challenge.ecommerceProyect.product.service.impl.PurchaseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseServiceImpl purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse purchase(@Valid @RequestBody PurchaseRequest request) {
        return purchaseService.purchase(request);
    }

    @GetMapping
    public List<PurchaseResponse> findAll() {
        return purchaseService.findAll();
    }

    @GetMapping("/{id}")
    public PurchaseResponse findById(@PathVariable Long id) {
        return purchaseService.findById(id);
    }
}
