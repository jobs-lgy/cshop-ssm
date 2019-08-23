package com.cshop.controller.goods;

import com.cshop.response.Result;
import com.cshop.entity.Sku;
import com.cshop.service.SkuSearchService;
import com.cshop.service.SkuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkuSearchController {

    @Reference
    private SkuSearchService skuSearchService;

    @Reference
    private SkuService skuService;

    @GetMapping("/importSkuList")
    public Result importSkuList() {
        List<Sku> skuList = skuService.findAll();
        skuSearchService.importSkuList(skuList);
        return new Result();
    }


}
