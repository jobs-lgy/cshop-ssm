package com.cshop.service;

import com.cshop.domain.Cart;

import java.util.List;

public interface CartService {
    void add(Cart cart);

    List<Cart> findAll(Long userId);

    Cart updateNum(Long userId, Long skuId, Integer num);

    void delete(Long userId, String skuId);
}
