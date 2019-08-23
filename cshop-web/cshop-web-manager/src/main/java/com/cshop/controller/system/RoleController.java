package com.cshop.controller.system;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Role;
import com.cshop.service.RoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @GetMapping("/findAll")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Role> findPage(int page, int size) {
        return roleService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Role> findList(@RequestBody Role role) {
        return roleService.findList(role);
    }

    @PostMapping("/findPage")
    public PageResult<Role> findPage(@RequestBody Role role, int page, int size) {
        return roleService.findPage(role, page, size);
    }

    @GetMapping("/findById")
    public Role findById(Long id) {
        return roleService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Role role) {
        roleService.add(role);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        roleService.delete(id);
        return new Result();
    }

}
