package com.cshop.controller;

import com.cshop.domain.Cart;
import com.cshop.service.CartService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller(value = "cart")
public class CartController {

    @Reference
    private CartService cartService;

    @PostMapping("add")
    public void addCart(@RequestBody Cart cart) {
        this.cartService.add(cart);
    }

    @GetMapping("/findAll")
    public List<Cart> findAll() {
        Long userId = 1L;
        return this.cartService.findAll(userId);
    }

    @PostMapping("/update")
    public Cart updateNum(Long skuId, Integer num) {
        Long userId = 1L;
        return this.cartService.updateNum(userId, skuId, num);
    }

    @DeleteMapping("/delete")
    public void deleteCart(String skuId) {
        Long userId = 1L;
        this.cartService.delete(userId, skuId);
    }
}
