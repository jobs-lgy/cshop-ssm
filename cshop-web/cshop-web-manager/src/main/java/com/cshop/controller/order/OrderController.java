package com.cshop.controller.order;

import com.cshop.entity.Order;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @GetMapping("/findAll")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Order> findPage(int page, int size) {
        return orderService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Order> findList(@RequestBody Order order) {
        return orderService.findList(order);
    }

    @PostMapping("/findPage")
    public PageResult<Order> findPage(@RequestBody Order order, int page, int size) {
        return orderService.findPage(order, page, size);
    }

    @GetMapping("/findById")
    public Order findById(Long id) {
        return orderService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Order order) {
        //1、检查是否登陆
        //2、检查是否创建订单地址
        //3、预减库存
        orderService.add(order);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Order order) {
        orderService.update(order);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        orderService.delete(id);
        return new Result();
    }
}
