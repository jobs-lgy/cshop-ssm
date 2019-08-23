package com.cshop.service.impl;

import com.cshop.entity.Area;
import com.cshop.repository.AreaMapper;
import com.cshop.repository.BaseRepository;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.AreaService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl extends AbstractBaseSerivce<Area, Long> implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public BaseRepository<Area, Long> getRepository() {
        return areaMapper;
    }

    @Override
    public Predicate buildPageParams(Root<Area> root, Area entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getCityId())) {
            predicates.add(cb.equal(root.get("cityId").as(String.class), entity.getCityId()));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
