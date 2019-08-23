package com.cshop.service;

import com.cshop.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * menu业务逻辑层
 */
public interface MenuService extends BaseService<Menu, Long> {
    /**
     * 获取全部菜单
     *
     * @return
     */
    public List<Map> findAllMenu();

    /**
     * 根据登陆名获取菜单列表
     *
     * @return
     */
    public List<Map> findMenuListByLoginName(String loginName);

}
