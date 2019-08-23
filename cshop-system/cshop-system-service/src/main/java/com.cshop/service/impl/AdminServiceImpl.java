package com.cshop.service.impl;

import com.cshop.entity.Admin;
import com.cshop.entity.AdminRole;
import com.cshop.repository.AdminMapper;
import com.cshop.repository.AdminRoleMapper;
import com.cshop.repository.BaseRepository;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.AdminService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl extends AbstractBaseSerivce<Admin, Long> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public BaseRepository<Admin, Long> getRepository() {
        return adminMapper;
    }

    @Override
    public void saveAdminRoles(Long adminId, Long[] roleIds) {
        //删除用户原来的角色列表
//        Example example= new Example(AdminRole.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("adminId",adminId);
//        adminRoleMapper.deleteByExample(example);
        //循环添加角色
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleMapper.save(adminRole);
        }
    }

    @Override
    public Predicate buildPageParams(Root<Admin> root, Admin entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getLoginName())) {
            predicates.add(cb.like(root.get("loginName").as(String.class), "%" + entity.getLoginName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getStatus())) {
            predicates.add(cb.equal(root.get("status").as(String.class), entity.getStatus()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
