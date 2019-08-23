package com.cshop.service.impl;

import com.cshop.entity.Activity;
import com.cshop.repository.ActivityMapper;
import com.cshop.repository.BaseRepository;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.ActivityService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl extends AbstractBaseSerivce<Activity, Long> implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public BaseRepository<Activity, Long> getRepository() {
        return activityMapper;
    }

    @Override
    public Predicate buildPageParams(Root<Activity> root, Activity entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getTitle())) {
            predicates.add(cb.like(root.get("title").as(String.class), "%" + entity.getTitle() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getStatus())) {
            predicates.add(cb.equal(root.get("status").as(String.class), entity.getStatus()));
        }

        if (!StringUtils.isEmpty(entity.getContent())) {
            predicates.add(cb.like(root.get("content").as(String.class), "%" + entity.getContent() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
