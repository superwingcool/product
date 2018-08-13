package com.wing.product.repository;

import com.wing.product.entity.ProductInfo;
import com.wing.product.enums.ProductStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(ProductStatusEnum productStatus);
}