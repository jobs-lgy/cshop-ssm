package com.cshop.service.impl;

import com.cshop.entity.Stock;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.StockMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.StockService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl extends AbstractBaseSerivce<Stock, Long> implements StockService {
    @Autowired
    StockMapper stockapper;

    @Override
    public BaseRepository<Stock, Long> getRepository() {
        return stockapper;
    }

    @Override
    public Predicate buildPageParams(Root<Stock> root, Stock entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
