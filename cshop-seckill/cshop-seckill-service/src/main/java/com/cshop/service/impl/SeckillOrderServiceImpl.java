package com.cshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cshop.entity.Order;
import com.cshop.entity.OrderAddress;
import com.cshop.entity.SeckillGoods;
import com.cshop.repository.SeckillGoodsMapper;
import com.cshop.service.OrderAddressService;
import com.cshop.service.OrderService;
import com.cshop.service.SeckillOrderService;
import com.cshop.service.StockService;
import com.cshop.util.IdWorker;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Reference
    private OrderAddressService orderAddressService;

    @Reference
    private OrderService orderService;

    @Reference
    private StockService stockService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ConcurrentHashMap<Long, Boolean> goodsStockSelloutMap = new ConcurrentHashMap<Long, Boolean>();

    @Autowired
    private IdWorker idWorker;

    @Override
    public void add(Long userId, Long seckillGoodsId) {
        //2、检查是否创建订单地址
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setUserId(userId);
        //from cache
        List<OrderAddress> orderAddressList = orderAddressService.findList(orderAddress);
        if (orderAddressList == null || orderAddressList.size() == 0) {
            throw new RuntimeException("请先创建订单地址");
        }

        //from cache
        SeckillGoods seckillGoods = seckillGoodsMapper.findById(seckillGoodsId).orElse(null);
        if (seckillGoods == null) {
            throw new RuntimeException("秒杀商品不存在");
        }
        Date now = new Date();
        Date startTime = seckillGoods.getStartTime();
        Date endTime = seckillGoods.getEndTime();
        //校验是否在秒杀时间范围内
        if (startTime.after(now) || endTime.before(now)) {
            throw new RuntimeException("不在秒杀时间内");
        }

        //内存标记判断
        if (!reduceStockInRedis(seckillGoods.getSkuId())) {
            throw new RuntimeException("商品已售完");
        }

        //redis预减库存
        if (!goodsStockSelloutMap.get(seckillGoods.getSkuId())) {
            throw new RuntimeException("商品已售完");
        }

        Order order = createOrder(userId, seckillGoods);

        //异步下单
        rabbitTemplate.convertAndSend("", "queue.seckillOrder", JSON.toJSON(order));
    }

    @Override
    public Boolean checkOrderIsCreated(Long userId, Long seckillGoodsId) {
        //从缓存中查询是否有秒杀创建的订单

//        //如果缓存没有，则从数据库查询商品，检查库存
//        SeckillGoods seckillGoods = seckillGoodsMapper.findById(seckillGoodsId).orElse(null);
//        if (seckillGoods == null) {
//            return false;
//        }
//        Stock stock = stockService.findById(seckillGoods.getSkuId());
//        if (stock.getSeckillNum() <= 0) {
//            throw new RuntimeException("商品已售完");
//        }

        //查询redis中订单排队等待标记
        return null;
    }

    private boolean reduceStockInRedis(Long skuId) {
        Long result = stringRedisTemplate.opsForValue().increment("SECKILL_STOCK_" + skuId, -1);
        if (result == null || result <= 0) {
            goodsStockSelloutMap.put(skuId, true);
            return false;
        }
        return true;
    }

    private Order createOrder(Long userId, SeckillGoods seckillGoods) {
        //订单表
        Order order = new Order();
        order.setId(idWorker.nextId());
        order.setUserId(userId);

        //订单明细
        return order;
    }
}
