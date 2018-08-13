package com.wing.product.controller;

import com.wing.product.entity.ProductInfo;
import com.wing.product.service.ProductService;
import com.wing.product.util.ResultVOUtil;
import com.wing.product.vo.ProductVO;
import com.wing.product.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResultVO list() {

        List<ProductInfo> products = productService.findUpAll();
        List<ProductVO> productVOs = new ArrayList<>();
        products.forEach(p -> {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(p, productVO);
            productVO.setCategoryId(p.getProductCategory().getCategoryId());
            productVO.setCategoryName(p.getProductCategory().getCategoryName());
            productVOs.add(productVO);
        });
        return ResultVOUtil.success(productVOs);
    }
}
