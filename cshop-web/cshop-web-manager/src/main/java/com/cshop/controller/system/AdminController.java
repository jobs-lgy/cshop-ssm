package com.cshop.controller.system;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.entity.Admin;
import com.cshop.service.AdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Reference
    private AdminService adminService;

    @GetMapping("/findAll")
    public List<Admin> findAll() {
        return adminService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Admin> findPage(int page, int size) {
        return adminService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Admin> findList(@RequestBody Admin admin) {
        return adminService.findList(admin);
    }

    @PostMapping("/findPage")
    public PageResult<Admin> findPage(@RequestBody Admin admin, int page, int size) {
        return adminService.findPage(admin, page, size);
    }

    @GetMapping("/findById")
    public Admin findById(Long id) {
        return adminService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        adminService.delete(id);
        return new Result();
    }

    /**
     * 保存管理员角色
     *
     * @param adminId
     * @param roleIds
     */
    @PostMapping("/saveAdminRoles")
    public Result saveAdminRoles(Long adminId, @RequestBody Long[] roleIds) {
        adminService.saveAdminRoles(adminId, roleIds);
        return new Result();
    }


}
