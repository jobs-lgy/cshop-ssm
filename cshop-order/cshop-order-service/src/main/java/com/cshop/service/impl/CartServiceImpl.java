package com.cshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cshop.domain.Cart;
import com.cshop.entity.Sku;
import com.cshop.service.CartService;
import com.cshop.service.SkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static String KEY_PREFIX = "leyou:cart:uid:";

    @Reference
    private SkuService skuService;

    @Override
    public void add(Cart cart) {
        //1.获取用户
        //2.Redis的key
        String key = KEY_PREFIX + cart.getUserId();
        //3.获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //4.查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean result = hashOperations.hasKey(skuId.toString());
        if (result) {
            //5.存在，获取购物车数据
            String json = hashOperations.get(skuId.toString()).toString();
            cart = JSON.parseObject(json, Cart.class);
            //6.修改购物车数量
            cart.setNum(cart.getNum() + num);
        } else {
            //7.不存在，新增购物车数据
            Sku sku = skuService.findById(cart.getSkuId());
            cart.setImages(sku.getImages());
            cart.setPrice(sku.getPrice());
            cart.setName(sku.getName());
            cart.setSpecs(sku.getSpecs());
        }
        //9.将购物车数据写入redis
        hashOperations.put(cart.getSkuId().toString(), JSON.toJSON(cart));
    }


    @Override
    public List<Cart> findAll(Long userId) {
        //1.获取登录的用户信息
        //2.判断是否存在购物车
        String key = KEY_PREFIX + userId;
        if (!this.stringRedisTemplate.hasKey(key)) {
            //3.不存在直接返回
            return new ArrayList<>();
        }
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        List<Object> carts = hashOperations.values();
        //4.判断是否有数据
        if (CollectionUtils.isEmpty(carts)) {
            return new ArrayList<>();
        }
        //5.查询购物车数据
        return carts.stream().map(o -> JSON.parseObject(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    @Override
    public Cart updateNum(Long userId, Long skuId, Integer num) {
        //1.获取登录用户
        String key = KEY_PREFIX + userId;
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //2.获取购物车
        String json = hashOperations.get(skuId.toString()).toString();
        Cart cart = JSON.parseObject(json, Cart.class);
        cart.setNum(num);
        //3.写入购物车
        hashOperations.put(skuId.toString(), JSON.toJSON(cart));
        return cart;
    }

    @Override
    public void delete(Long userId,String skuId) {
        //1.获取登录用户
        String key = KEY_PREFIX + userId;
        BoundHashOperations<String, Object, Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //2.删除商品
        hashOperations.delete(skuId);
    }
}
