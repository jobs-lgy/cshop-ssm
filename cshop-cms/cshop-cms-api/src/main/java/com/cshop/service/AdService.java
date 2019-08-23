package com.cshop.service;


import com.cshop.entity.Ad;

import java.util.List;

/**
 * ad业务逻辑层
 */
public interface AdService extends BaseService<Ad, Long> {
    /**
     * 根据广告位置查询广告列表
     *
     * @param position
     * @return
     */
    public List<Ad> findByPosition(String position);

    /**
     * 保存广告到redis
     *
     * @param position
     */
    public void saveAdByPosition(String position);

    /**
     * 保存全部广告到redis
     */
    public void saveAllAd();

}
