package com.cshop.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 初始化服务类
 */
@Service
public class InitService implements InitializingBean {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuService skuService;

    public void afterPropertiesSet() throws Exception {
//        categoryService.saveCategoryTreeToRedis();//加载商品分类导航缓存
//        skuService.saveAllPriceToRedis();//加载价格数据
    }
}
