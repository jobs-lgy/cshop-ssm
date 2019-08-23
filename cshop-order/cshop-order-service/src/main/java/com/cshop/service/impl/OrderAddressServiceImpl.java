package com.cshop.service.impl;

import com.cshop.entity.OrderAddress;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.OrderAddressMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.OrderAddressService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAddressServiceImpl extends AbstractBaseSerivce<OrderAddress, Long> implements OrderAddressService {
    @Autowired
    private OrderAddressMapper orderAddressMapper;

    @Override
    public BaseRepository<OrderAddress, Long> getRepository() {
        return orderAddressMapper;
    }

    @Override
    public Predicate buildPageParams(Root<OrderAddress> root, OrderAddress entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getUserId())) {
            predicates.add(cb.equal(root.get("userId").as(Long.class), entity.getUserId()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
