package com.cshop.service.impl;

import com.cshop.entity.SeckillGoods;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.SeckillGoodsMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.SeckillGoodsService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeckillGoodsServiceImpl extends AbstractBaseSerivce<SeckillGoods, Long> implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public BaseRepository<SeckillGoods, Long> getRepository() {
        return seckillGoodsMapper;
    }

    @Override
    public Predicate buildPageParams(Root<SeckillGoods> root, SeckillGoods entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
