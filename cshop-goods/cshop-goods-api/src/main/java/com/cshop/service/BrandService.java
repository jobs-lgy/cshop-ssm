package com.cshop.service;

import com.cshop.entity.Brand;
import com.cshop.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层
 */
public interface BrandService extends BaseService<Brand, Long> {
    public List<Map> findAllByCategoryName(String categoryName);
}
