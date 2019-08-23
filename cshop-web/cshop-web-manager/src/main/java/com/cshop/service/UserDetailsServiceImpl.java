package com.cshop.service;

import com.cshop.entity.Admin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private ResourceService resourceService;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询管理员
        Admin admin = new Admin();
        admin.setLoginName(s);
        admin.setStatus("1");
        List<Admin> list = adminService.findList(admin);
        if (list.size() == 0) {
            return null;
        }
        //构建权限列表
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        List<String> resKeyList = resourceService.findResKeyByLoginName(s);
        for (String resKey : resKeyList) {
            grantedAuths.add(new SimpleGrantedAuthority(resKey));
        }
        return new User(s, list.get(0).getPassword(), grantedAuths);
    }
}
