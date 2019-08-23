package com.cshop.service;

import com.cshop.entity.Category;
import com.cshop.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层
 */
public interface CategoryService extends BaseService<Category, Long> {
    /**
     * 查询分类(树形结构)
     *
     * @return
     */
    public List<Map> findCategoryTree();

    /**
     * 将商品分类树放入缓存
     */
    public void saveCategoryTreeToRedis();

}
