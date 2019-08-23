package com.cshop.service.task;

import com.cshop.service.AdService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Init implements InitializingBean {

    @Autowired
    private AdService adService;

    public void afterPropertiesSet() throws Exception {
        System.out.println("---缓存预热---");
//        adService.saveAllAd();
    }
}
