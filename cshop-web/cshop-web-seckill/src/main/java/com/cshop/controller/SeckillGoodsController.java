package com.cshop.controller;

import com.cshop.entity.SeckillGoods;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.SeckillGoodsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsController {
    @Reference
    private SeckillGoodsService seckillGoodsService;

    @GetMapping("/findAll")
    public List<SeckillGoods> findAll() {
        return seckillGoodsService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<SeckillGoods> findPage(int page, int size) {
        return seckillGoodsService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<SeckillGoods> findList(@RequestBody SeckillGoods seckillGoods) {
        return seckillGoodsService.findList(seckillGoods);
    }

    @PostMapping("/findPage")
    public PageResult<SeckillGoods> findPage(@RequestBody SeckillGoods seckillGoods, int page, int size) {
        return seckillGoodsService.findPage(seckillGoods, page, size);
    }

    @GetMapping("/findById")
    public SeckillGoods findById(Long id) {
        return seckillGoodsService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody SeckillGoods seckillGoods) {
        seckillGoodsService.add(seckillGoods);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody SeckillGoods seckillGoods) {
        seckillGoodsService.update(seckillGoods);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        seckillGoodsService.delete(id);
        return new Result();
    }

}
