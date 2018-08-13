package com.wing.product.service;

import com.wing.product.entity.ProductInfo;
import com.wing.product.enums.ProductStatusEnum;
import com.wing.product.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductInfoRepository productRepository;

    public Optional<ProductInfo> findOne(String productId) {
        return productRepository.findById(productId);
    }


    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnum.UP);
    }
}