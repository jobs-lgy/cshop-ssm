package com.cshop.controller.goods;

import com.cshop.entity.Param;
import com.cshop.entity.Param;
import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.service.ParamService;
import com.cshop.service.ParamService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/param")
@CrossOrigin
public class ParamController {

    @Reference
    private ParamService paramService;

    @GetMapping("/findAll")
    public List<Param> findAll() {
        return paramService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Param> findPage(int page, int size) {
        return paramService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Param> findList(@RequestBody Param param) {
        return paramService.findList(param);
    }

    @PostMapping("/findPage")
    public PageResult<Param> findPage(@RequestBody Param param, int page, int size) {
        return paramService.findPage(param, page, size);
    }

    @GetMapping("/findById")
    public Param findById(Long id) {
        return paramService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Param param) {
        paramService.add(param);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Param param) {
        paramService.update(param);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        paramService.delete(id);
        return new Result();
    }

}
