package com.cshop.controller;

import com.cshop.response.Result;
import com.cshop.service.SeckillOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "seckill")
public class SeckillOrderController {
    @Reference
    private SeckillOrderService seckillOrderService;

    @GetMapping(value = "/add")
    public Result add(Long seckillGoodsId) {
        //1、检查是否登陆
        Long userId = 1L;
        if (userId == null) {
            return new Result(403, "请先登录再进行操作");
        }
        seckillOrderService.add(userId, seckillGoodsId);
        return new Result();
    }

    @GetMapping(value = "/check")
    public Result checkOrderIsCreated(Long seckillGoodsId) {
        //1、检查是否登陆
        Long userId = 1L;
        if (userId == null) {
            return new Result(403, "请先登录再进行操作");
        }
        Boolean result = seckillOrderService.checkOrderIsCreated(userId, seckillGoodsId);

        if (result) {
            return new Result();
        }

        return new Result();
    }
}
