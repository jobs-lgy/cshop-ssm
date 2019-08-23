package com.cshop.service.impl;

import com.cshop.entity.OrderStatus;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.OrderStatusMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.OrderStatusService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderStatusServiceImpl extends AbstractBaseSerivce<OrderStatus, Long> implements OrderStatusService {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Override
    public BaseRepository<OrderStatus, Long> getRepository() {
        return orderStatusMapper;
    }

    @Override
    public Predicate buildPageParams(Root<OrderStatus> root, OrderStatus entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
