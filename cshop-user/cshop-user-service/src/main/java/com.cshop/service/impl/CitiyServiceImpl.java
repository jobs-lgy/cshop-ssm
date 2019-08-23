package com.cshop.service.impl;

import com.cshop.entity.City;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.CityMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.CityService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitiyServiceImpl extends AbstractBaseSerivce<City, Long> implements CityService {

    @Autowired
    private CityMapper cityMapper;


    @Override
    public BaseRepository<City, Long> getRepository() {
        return cityMapper;
    }

    @Override
    public Predicate buildPageParams(Root<City> root, City entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
