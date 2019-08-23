package com.cshop.service;

import java.util.List;

/**
 * 资源业务逻辑层
 */
public interface ResourceService {
    public List<String> findResKeyByLoginName(String loginName);
}
