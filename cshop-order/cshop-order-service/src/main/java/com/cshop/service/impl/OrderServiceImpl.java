package com.cshop.service.impl;

import com.cshop.entity.Order;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.OrderDetailMapper;
import com.cshop.repository.OrderMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.OrderService;
import com.cshop.service.SkuService;
import com.cshop.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl extends AbstractBaseSerivce<Order, Long> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Reference
    private SkuService skuService;




    @Override
    public BaseRepository<Order, Long> getRepository() {
        return orderMapper;
    }

    /**
     * 新增
     *
     * @param order
     */
    @Transactional
    public Order add(Order order) {
        return order;
    }

    @Override
    public Predicate buildPageParams(Root<Order> root, Order entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getShippingName())) {
            predicates.add(cb.like(root.get("shippingName").as(String.class), "%" + entity.getShippingName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getShippingCode())) {
            predicates.add(cb.like(root.get("shippingCode").as(String.class), "%" + entity.getShippingCode() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
