package com.cshop.controller.goods;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Category;
import com.cshop.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
    @Reference
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Category> findPage(int page, int size) {
        return categoryService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Category> findList(@RequestBody Category category) {
        return categoryService.findList(category);
    }

    @PostMapping("/findPage")
    public PageResult<Category> findPage(@RequestBody Category category, int page, int size) {
        return categoryService.findPage(category, page, size);
    }

    @GetMapping("/findById")
    public Category findById(Long id) {
        return categoryService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        categoryService.delete(id);
        return new Result();
    }
}
