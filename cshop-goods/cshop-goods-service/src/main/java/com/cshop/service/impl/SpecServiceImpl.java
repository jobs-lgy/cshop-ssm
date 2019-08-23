package com.cshop.service.impl;

import com.cshop.entity.Spec;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.SpecMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.SpecService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecServiceImpl extends AbstractBaseSerivce<Spec, Long> implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Override
    public BaseRepository<Spec, Long> getRepository() {
        return specMapper;
    }

    @Override
    public Predicate buildPageParams(Root<Spec> root, Spec entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getOptions())) {
            predicates.add(cb.like(root.get("options").as(String.class), "%" + entity.getOptions() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getCategoryId())) {
            predicates.add(cb.equal(root.get("categoryId").as(String.class), entity.getCategoryId()));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
