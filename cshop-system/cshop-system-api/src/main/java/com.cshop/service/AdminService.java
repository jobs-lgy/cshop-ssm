package com.cshop.service;

import com.cshop.entity.Admin;

/**
 * admin业务逻辑层
 */
public interface AdminService extends BaseService<Admin, Long> {
    /**
     * 保存管理员角色
     *
     * @param adminId
     * @param roleIds
     */
    public void saveAdminRoles(Long adminId, Long[] roleIds);

}
