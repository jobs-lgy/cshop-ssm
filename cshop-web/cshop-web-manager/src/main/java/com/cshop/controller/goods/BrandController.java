package com.cshop.controller.goods;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Brand;
import com.cshop.service.BrandService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Reference
    private BrandService brandService;

    @PreAuthorize("hasAuthority('brand')")
    @GetMapping("/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Brand> findPage(int page, int size) {
        return brandService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Brand> findList(@RequestBody Brand brand) {
        return brandService.findList(brand);
    }

    @PostMapping("/findPage")
    public PageResult<Brand> findPage(@RequestBody Brand brand, int page, int size) {
        return brandService.findPage(brand, page, size);
    }

    @GetMapping("/findById")
    public Brand findById(Long id) {
        return brandService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Brand brand) {
        brandService.update(brand);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        brandService.delete(id);
        return new Result();
    }

}
