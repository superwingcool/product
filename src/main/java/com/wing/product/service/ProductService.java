package com.wing.product.service;

import com.wing.product.entity.ProductInfo;
import com.wing.product.enums.ProductStatusEnum;
import com.wing.product.exception.ProductException;
import com.wing.product.repository.ProductInfoRepository;
import com.wing.product.util.JsonMapper;
import com.wing.product.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.web.client.feign.TraceFeignClientAutoConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.wing.product.enums.ResultEnum.PRODUCT_NOT_EXIST;
import static com.wing.product.enums.ResultEnum.PRODUCT_STOCK_ERROR;

@Service
public class ProductService {

    @Autowired
    private ProductInfoRepository productRepository;

    @Autowired
    private Source source;

    public Optional<ProductInfo> findOne(String productId) {
        return productRepository.findById(productId);
    }


    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnum.UP);
    }

    public List<ProductInfo> getProductsByIds(String[] productIds) {

        return productRepository.findByProductIdIn(Arrays.asList(productIds));
//        List<ProductVO> productVOs = new ArrayList<>();
//        products.forEach(p -> {
//            ProductVO productVO = new ProductVO();
//            BeanUtils.copyProperties(p, productVO);
//            productVO.setCategoryId(p.getProductCategory().getCategoryId());
//            productVO.setCategoryName(p.getProductCategory().getCategoryName());
//            productVOs.add(productVO);
//        });
//        return productVOs;
    }

    public void decreaseStock(List<CartVO> carts) {
        List<ProductInfo> products = productRepository.findByProductIdIn(CartVO.getProductIds(carts));
        if (products.size() != carts.size()) {
            throw new ProductException(PRODUCT_NOT_EXIST);
        }
        products.forEach(p -> {
            CartVO cart = carts.stream()
                    .filter(c -> p.getProductStock() > c.getProductQuantity())
                    .findFirst()
                    .orElseThrow(() -> new ProductException(PRODUCT_STOCK_ERROR));
            p.setProductStock(p.getProductStock() - cart.getProductQuantity());
        });
        productRepository.saveAll(products);
        source.output().send(MessageBuilder.withPayload(JsonMapper.objectToJson(products)).build());
    }
}