package com.cshop.service.impl;

import com.cshop.entity.Brand;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.BrandMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.BrandService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl extends AbstractBaseSerivce<Brand, Long> implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    @Override
    public BaseRepository<Brand, Long> getRepository() {
        return brandMapper;
    }

    @Override
    public Predicate buildPageParams(Root<Brand> root, Brand entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getLetter())) {
            predicates.add(cb.like(root.get("letter").as(String.class), "%" + entity.getLetter() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    @Override
    public List<Map> findAllByCategoryName(String categoryName) {
        return brandMapper.findAllByCategoryName(categoryName);
    }
}
