package com.ecommerce.challenge.ecommerceProyect.product.repository;

import com.ecommerce.challenge.ecommerceProyect.product.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}

