package com.cshop.controller.system;

import com.cshop.service.MenuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @GetMapping("/findMenu")
    public List<Map> findMenu() {
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        return menuService.findMenuListByLoginName(loginName);
    }


}
