package com.cshop.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cshop.entity.Role;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.RoleMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends AbstractBaseSerivce<Role, Long> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public BaseRepository<Role, Long> getRepository() {
        return roleMapper;
    }

    public Predicate buildPageParams(Root<Role> root, Role entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
