package com.cshop.service.impl;

import com.cshop.entity.Province;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.ProvinceMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.ProvinceService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceServiceImpl extends AbstractBaseSerivce<Province, Long> implements ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public BaseRepository<Province, Long> getRepository() {
        return provinceMapper;
    }

    @Override
    public Predicate buildPageParams(Root<Province> root, Province entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
