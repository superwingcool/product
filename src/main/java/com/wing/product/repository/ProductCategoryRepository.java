package com.wing.product.repository;

import com.wing.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
