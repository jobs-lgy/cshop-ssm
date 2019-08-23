package com.cshop.controller.goods;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Spec;
import com.cshop.service.SpecService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {

    @Reference
    private SpecService specService;

    @GetMapping("/findAll")
    public List<Spec> findAll() {
        return specService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Spec> findPage(int page, int size) {
        return specService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Spec> findList(@RequestBody Spec spec) {
        return specService.findList(spec);
    }

    @PostMapping("/findPage")
    public PageResult<Spec> findPage(@RequestBody Spec spec, int page, int size) {
        return specService.findPage(spec, page, size);
    }

    @GetMapping("/findById")
    public Spec findById(Long id) {
        return specService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Spec spec) {
        specService.add(spec);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Spec spec) {
        specService.update(spec);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        specService.delete(id);
        return new Result();
    }

}
