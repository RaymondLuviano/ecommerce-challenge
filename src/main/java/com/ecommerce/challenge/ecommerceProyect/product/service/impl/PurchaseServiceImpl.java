package com.ecommerce.challenge.ecommerceProyect.product.service.impl;

import com.ecommerce.challenge.ecommerceProyect.exceptions.BusinessException;
import com.ecommerce.challenge.ecommerceProyect.exceptions.ResourceNotFoundException;
import com.ecommerce.challenge.ecommerceProyect.product.dto.PurchaseRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.PurchaseResponse;
import com.ecommerce.challenge.ecommerceProyect.product.entity.ProductEntity;
import com.ecommerce.challenge.ecommerceProyect.product.entity.PurchaseEntity;
import com.ecommerce.challenge.ecommerceProyect.product.entity.PurchaseStatus;
import com.ecommerce.challenge.ecommerceProyect.product.repository.ProductRepository;
import com.ecommerce.challenge.ecommerceProyect.product.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    @Transactional
    public PurchaseResponse purchase(PurchaseRequest request) {
        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + request.productId()
                ));

        if (product.getStock() < request.quantity()) {
            throw new BusinessException("Not enough stock for product: " + product.getName());
        }

        fakePayment();

        product.setStock(product.getStock() - request.quantity());

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setProduct(product);
        purchase.setQuantity(request.quantity());
        purchase.setUnitPrice(product.getPrice());
        purchase.setTotal(product.getPrice().multiply(BigDecimal.valueOf(request.quantity())));
        purchase.setStatus(PurchaseStatus.APPROVED);

        PurchaseEntity savedPurchase = purchaseRepository.save(purchase);

        return toResponse(savedPurchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> findAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseResponse findById(Long id) {
        return purchaseRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));
    }

    private void fakePayment() {
        boolean paymentApproved = true;

        if (!paymentApproved) {
            throw new BusinessException("Payment was rejected");
        }
    }

    private PurchaseResponse toResponse(PurchaseEntity purchase) {
        ProductEntity product = purchase.getProduct();

        return new PurchaseResponse(
                purchase.getId(),
                product.getId(),
                product.getName(),
                product.getSku(),
                purchase.getQuantity(),
                purchase.getUnitPrice(),
                purchase.getTotal(),
                purchase.getStatus(),
                purchase.getCreatedAt()
        );
    }
}