package com.cshop.service.impl;

import com.cshop.CacheKey;
import com.cshop.entity.Ad;
import com.cshop.repository.AdMapper;
import com.cshop.repository.BaseRepository;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.AdService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdServiceImpl extends AbstractBaseSerivce<Ad, Long> implements AdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseRepository<Ad, Long> getRepository() {
        return adMapper;
    }

    /**
     * 新增
     *
     * @param ad
     */
    public Ad add(Ad ad) {
        Ad dbAd = adMapper.save(ad);
        saveAdByPosition(ad.getPosition());//重新加载缓存
        return dbAd;
    }

    /**
     * 修改
     *
     * @param ad
     */
    public Ad update(Ad ad) {
        //清除修改前的缓存
        String position = adMapper.findById(ad.getId()).orElse(null).getPosition();
        saveAdByPosition(position);//重新加载缓存
        Ad dbAd = adMapper.saveAndFlush(ad);
        saveAdByPosition(ad.getPosition());//重新加载缓存
        return dbAd;
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        //清除修改前的缓存
        String position = adMapper.findById(id).orElse(null).getPosition();
        saveAdByPosition(position);//重新加载缓存
        adMapper.deleteById(id);
    }

    public List<Ad> findByPosition(String position) {
        //从缓存中查询广告列表
        return (List<Ad>) redisTemplate.boundHashOps(CacheKey.AD).get(position);
    }


    public void saveAdByPosition(String position) {
        //从数据库中查询
        Ad ad = new Ad();
        ad.setPosition(position);
        ad.setStartTime(new Date());//开始时间小于当前时间
        ad.setEndTime(new Date()); //截至时间大于当前时间
        ad.setStatus("1");

        List<Ad> adList = adMapper.findAll(Example.of(ad));
        redisTemplate.boundHashOps(CacheKey.AD).put(position, adList);//放入缓存
    }

    /**
     * 获取所有的广告列表
     *
     * @return
     */
    private List<String> getPositions() {
        List<String> positions = new ArrayList<String>();
        positions.add("web_index_lb");//首页广告轮播图
        //.....略
        return positions;
    }

    /**
     * 预加载所有广告
     */
    public void saveAllAd() {
        for (String position : getPositions()) {
            saveAdByPosition(position);
        }
    }

    @Override
    public Predicate buildPageParams(Root<Ad> root, Ad entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getStatus())) {
            predicates.add(cb.equal(root.get("status").as(String.class), entity.getStatus()));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
