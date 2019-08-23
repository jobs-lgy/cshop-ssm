package com.cshop.service;

public interface SeckillOrderService {
    void add(Long userId, Long seckillGoodsId);

    Boolean checkOrderIsCreated(Long userId, Long seckillGoodsId);
}
