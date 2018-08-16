package com.wing.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartVO {

    private String productId;

    private Integer productQuantity;

    public static List<String> getProductIds(List<CartVO> carts) {
        List<String> ids = new ArrayList<>();
        carts.forEach(cart -> {
            ids.add(cart.getProductId());
        });
        return ids;
    }

}