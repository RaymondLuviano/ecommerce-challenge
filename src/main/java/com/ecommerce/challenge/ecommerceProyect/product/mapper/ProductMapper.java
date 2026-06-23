package com.ecommerce.challenge.ecommerceProyect.product.mapper;

import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductRequest;
import com.ecommerce.challenge.ecommerceProyect.product.dto.ProductResponse;
import com.ecommerce.challenge.ecommerceProyect.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(ProductRequest request);

    ProductResponse toResponse(ProductEntity entity);

    void updateEntity(ProductRequest request, @MappingTarget ProductEntity entity);
}