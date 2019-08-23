package com.cshop.controller.order;

import com.cshop.entity.OrderDetail;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.OrderDetailService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Reference
    private OrderDetailService orderDetailService;

    @GetMapping("/findAll")
    public List<OrderDetail> findAll() {
        return orderDetailService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<OrderDetail> findPage(int page, int size) {
        return orderDetailService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<OrderDetail> findList(@RequestBody OrderDetail orderItem) {
        return orderDetailService.findList(orderItem);
    }

    @PostMapping("/findPage")
    public PageResult<OrderDetail> findPage(@RequestBody OrderDetail orderItem, int page, int size) {
        return orderDetailService.findPage(orderItem, page, size);
    }

    @GetMapping("/findById")
    public OrderDetail findById(Long id) {
        return orderDetailService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody OrderDetail orderItem) {
        orderDetailService.add(orderItem);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OrderDetail orderItem) {
        orderDetailService.update(orderItem);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        orderDetailService.delete(id);
        return new Result();
    }


}
