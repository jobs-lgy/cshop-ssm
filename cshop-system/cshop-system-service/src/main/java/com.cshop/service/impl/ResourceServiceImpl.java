package com.cshop.service.impl;

import com.cshop.repository.ResourceMapper;
import com.cshop.service.ResourceService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<String> findResKeyByLoginName(String loginName) {
        return resourceMapper.findResKeyByLoginName(loginName);
    }
}
