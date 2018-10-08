package com.wing.product.controller;

import com.wing.product.entity.ProductInfo;
import com.wing.product.service.ProductService;
import com.wing.product.util.ResultVOUtil;
import com.wing.product.vo.CartVO;
import com.wing.product.vo.ProductVO;
import com.wing.product.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResultVO list() {
        log.info("1111111");
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

    @GetMapping("/{productIds}")
    public List<ProductInfo> getProductsByIds(@PathVariable("productIds") String[] productIds) throws InterruptedException {
        Thread.sleep(2000);
        log.info("2222");
        return productService.getProductsByIds(productIds);
    }

    @PutMapping("/decreaseStock")
    @ResponseStatus(HttpStatus.OK)
    public void decreaseStock(@RequestBody List<CartVO> carts) {
        productService.decreaseStock(carts);
    }
}
