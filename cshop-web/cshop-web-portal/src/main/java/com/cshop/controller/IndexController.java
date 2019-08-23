package com.cshop.controller;

import com.cshop.entity.Ad;
import com.cshop.service.AdService;
import com.cshop.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Reference
    private AdService adService;


    @Reference
    private CategoryService categoryService;

    /**
     * 网站首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        //查询首页轮播图
        List<Ad> lbtList = adService.findByPosition("web_index_lb");
        model.addAttribute("lbt", lbtList);
        //查询商品分类
        List<Map> categoryList = categoryService.findCategoryTree();
        model.addAttribute("categoryList", categoryList);


        return "index";
    }

}
