package com.cshop.service.impl;

import com.cshop.entity.OrderDetail;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.OrderDetailMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.OrderDetailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl extends AbstractBaseSerivce<OrderDetail, Long> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public BaseRepository<OrderDetail, Long> getRepository() {
        return orderDetailMapper;
    }

    @Override
    public Predicate buildPageParams(Root<OrderDetail> root, OrderDetail entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
