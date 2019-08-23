package com.cshop.service.impl;

import com.cshop.entity.LoginLog;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.LoginLogMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.LoginLogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginLogServiceImpl extends AbstractBaseSerivce<LoginLog, Long> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public BaseRepository<LoginLog, Long> getRepository() {
        return loginLogMapper;
    }

    @Override
    public Predicate buildPageParams(Root<LoginLog> root, LoginLog entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getIp())) {
            predicates.add(cb.like(root.get("ip").as(String.class), "%" + entity.getIp() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getLocation())) {
            predicates.add(cb.equal(root.get("location").as(String.class), entity.getLocation()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
