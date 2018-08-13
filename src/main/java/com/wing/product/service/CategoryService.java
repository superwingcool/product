package com.wing.product.service;

import com.wing.product.entity.ProductCategory;
import com.wing.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    public Optional<ProductCategory> findOne(Integer categoryId) {
        return productCategoryRepository.findById(categoryId);
    }


    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }


    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeIds) {
        return productCategoryRepository.findAllById(categoryTypeIds);
    }


    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
