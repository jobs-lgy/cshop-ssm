package com.cshop.service;

import com.cshop.domain.Goods;
import com.cshop.entity.Spu;
import com.cshop.service.BaseService;

/**
 * 业务逻辑层
 */
public interface SpuService extends BaseService<Spu, Long> {
    /**
     * 逻辑删除
     *
     * @param id
     */
    public void logicDelete(Long id);

    /**
     * 恢复数据
     *
     * @param id
     */
    public void restore(Long id);

    /**
     * 审核商品
     *
     * @param id
     */
    public void audit(Long id);

    /**
     * 下架商品
     *
     * @param id
     */
    public void pull(Long id);

    /**
     * 上架商品
     *
     * @param id
     */
    public void put(Long id);

    /**
     * 批量上架商品
     *
     * @param ids
     */
    public int putMany(Long[] ids);

    /**
     * 保存商品
     *
     * @param goods 商品组合实体类
     */
    public void saveGoods(Goods goods);


    /**
     * 根据ID查询商品
     *
     * @param id
     * @return
     */
    public Goods findGoodsById(Long id);

}
