package com.cshop.controller.order;

import com.cshop.entity.OrderStatus;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.OrderStatusService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController {

    @Reference
    private OrderStatusService orderStatusService;

    @GetMapping("/findAll")
    public List<OrderStatus> findAll() {
        return orderStatusService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<OrderStatus> findPage(int page, int size) {
        return orderStatusService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<OrderStatus> findList(@RequestBody OrderStatus orderItem) {
        return orderStatusService.findList(orderItem);
    }

    @PostMapping("/findPage")
    public PageResult<OrderStatus> findPage(@RequestBody OrderStatus orderItem, int page, int size) {
        return orderStatusService.findPage(orderItem, page, size);
    }

    @GetMapping("/findById")
    public OrderStatus findById(Long id) {
        return orderStatusService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody OrderStatus orderItem) {
        orderStatusService.add(orderItem);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OrderStatus orderItem) {
        orderStatusService.update(orderItem);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        orderStatusService.delete(id);
        return new Result();
    }
}
