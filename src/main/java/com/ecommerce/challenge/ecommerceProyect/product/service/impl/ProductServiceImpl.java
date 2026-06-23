package com.ecommerce.challenge.ecommerceProyect.product.service.impl;

import com.ecommerce.challenge.ecommerceProyect.exceptions.BusinessException;
import com.ecommerce.challenge.ecommerceProyect.exceptions.ResourceNotFoundException;
import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductResponse;
import com.ecommerce.challenge.ecommerceProyect.product.entity.ProductEntity;
import com.ecommerce.challenge.ecommerceProyect.product.mapper.ProductMapper;
import com.ecommerce.challenge.ecommerceProyect.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new BusinessException("Product already exists with sku: " + request.sku());
        }

        ProductEntity product = productMapper.toEntity(request);
        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.findBySku(request.sku())
                .filter(existingProduct -> !existingProduct.getId().equals(id))
                .ifPresent(existingProduct -> {
                    throw new BusinessException("Product already exists with sku: " + request.sku());
                });

        productMapper.updateEntity(request, product);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> search(String query) {
        return productRepository
                .findByNameContainingIgnoreCaseOrSkuContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                        query,
                        query,
                        query
                )
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
