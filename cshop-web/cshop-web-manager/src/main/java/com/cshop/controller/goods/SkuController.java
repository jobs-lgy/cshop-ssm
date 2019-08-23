package com.cshop.controller.goods;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Sku;
import com.cshop.service.SkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
@CrossOrigin
public class SkuController {

    @Reference
    private SkuService skuService;

    @GetMapping("/findAll")
    public List<Sku> findAll() {
        return skuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Sku> findPage(int page, int size) {
        return skuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Sku> findList(@RequestBody Sku sku) {
        return skuService.findList(sku);
    }

    @PostMapping("/findPage")
    public PageResult<Sku> findPage(@RequestBody Sku sku, int page, int size) {
        return skuService.findPage(sku, page, size);
    }

    @GetMapping("/findById")
    public Sku findById(Long id) {
        return skuService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Sku sku) {
        skuService.add(sku);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Sku sku) {
        skuService.update(sku);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        skuService.delete(id);
        return new Result();
    }

}
