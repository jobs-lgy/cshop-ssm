package com.cshop.service.impl;

import com.cshop.CacheKey;
import com.cshop.entity.Sku;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.SkuMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkuServiceImpl extends AbstractBaseSerivce<Sku, Long> implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseRepository<Sku, Long> getRepository() {
        return skuMapper;
    }

    public int findPrice(Long id) {
        //从缓存中查询
        return (Integer) redisTemplate.boundHashOps(CacheKey.SKU_PRICE).get(id);
    }


    public void savePriceToRedisBySkuId(Long skuId, Long price) {
        redisTemplate.boundHashOps(CacheKey.SKU_PRICE).put(skuId, price);
    }

    public void saveAllPriceToRedis() {
        //检查缓存是否存在价格数据
        if (!redisTemplate.hasKey("sku_price")) {
            System.out.println("加载全部数据");
            List<Sku> skuList = skuMapper.findAll();
            for (Sku sku : skuList) {
                    redisTemplate.boundHashOps("sku_price").put(sku.getId(), sku.getPrice());
            }
        } else {
            System.out.println("已存在价格数据，没有全部加载");
        }
    }


    @Override
    public Predicate buildPageParams(Root<Sku> root, Sku entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.equal(root.get("name").as(String.class), entity.getName()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
