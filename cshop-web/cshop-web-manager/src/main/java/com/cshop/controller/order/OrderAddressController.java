package com.cshop.controller.order;

import com.cshop.entity.OrderAddress;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.OrderAddressService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderAddress")
public class OrderAddressController {

    @Reference
    private OrderAddressService orderAddressService;

    @GetMapping("/findAll")
    public List<OrderAddress> findAll() {
        return orderAddressService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<OrderAddress> findPage(int page, int size) {
        return orderAddressService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<OrderAddress> findList(@RequestBody OrderAddress orderItem) {
        return orderAddressService.findList(orderItem);
    }

    @PostMapping("/findPage")
    public PageResult<OrderAddress> findPage(@RequestBody OrderAddress orderItem, int page, int size) {
        return orderAddressService.findPage(orderItem, page, size);
    }

    @GetMapping("/findById")
    public OrderAddress findById(Long id) {
        return orderAddressService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody OrderAddress orderItem) {
        orderAddressService.add(orderItem);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OrderAddress orderItem) {
        orderAddressService.update(orderItem);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        orderAddressService.delete(id);
        return new Result();
    }


}
