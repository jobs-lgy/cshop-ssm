package com.cshop.service;

import com.cshop.entity.Sku;

/**
 * 业务逻辑层
 */
public interface SkuService extends BaseService<Sku, Long> {
    /**
     * 查询价格
     *
     * @param id
     * @return
     */
    public int findPrice(Long id);

    /**
     * 保存价格到缓存
     *
     * @param skuId
     */
    public void savePriceToRedisBySkuId(Long skuId, Long price);

    /**
     * 保存全部价格到缓存
     */
    public void saveAllPriceToRedis();

}
