package com.ecommerce.challenge.ecommerceProyect.product.controller;

import com.ecommerce.challenge.ecommerceProyect.product.service.impl.PurchaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PurchaseViewController {

    private final PurchaseServiceImpl purchaseService;

    @GetMapping("/purchases/history")
    public String purchaseHistory(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase-history";
    }
}