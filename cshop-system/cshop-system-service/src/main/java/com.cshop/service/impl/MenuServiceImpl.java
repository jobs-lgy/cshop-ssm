package com.cshop.service.impl;

import com.cshop.entity.Menu;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.MenuMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.MenuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends AbstractBaseSerivce<Menu, Long> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public BaseRepository<Menu, Long> getRepository() {
        return menuMapper;
    }

    /**
     * 查询全部菜单
     *
     * @return
     */
    public List<Map> findAllMenu() {
        List<Menu> menuList = findAll();//查询全部菜单列表
        return findMenuListByParentId(menuList, 0L);//一级菜单列表
    }

    /**
     * 根据登陆名查询权限菜单
     *
     * @param loginName
     * @return
     */
    @Override
    public List<Map> findMenuListByLoginName(String loginName) {
        List<Menu> menuList = menuMapper.findMenuListByLoginName(loginName);//根据登陆名查询菜单列表
        return findMenuListByParentId(menuList, 0L);//一级菜单列表
    }


    /**
     * 查询下级菜单ID
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private List<Map> findMenuListByParentId(List<Menu> menuList, Long parentId) {
        List<Map> mapList = new ArrayList<>();
        for (Menu menu : menuList) {  //循环一级菜单
            if (menu.getParentId().equals(parentId)) {
                Map map = new HashMap();
                map.put("path", menu.getId());
                map.put("title", menu.getName());
                map.put("icon", menu.getIcon());
                map.put("linkUrl", menu.getUrl());
                map.put("children", findMenuListByParentId(menuList, menu.getId()));
                mapList.add(map);
            }
        }
        return mapList;
    }

    public Predicate buildPageParams(Root<Menu> root, Menu entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
