package com.cshop.controller;

import com.cshop.response.Result;
import com.cshop.entity.User;
import com.cshop.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Reference
    private UserService userService;

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    @GetMapping(value = "/sendSms")
    public Result sendSms(String phone) {
        userService.sendSms(phone);
        return new Result();
    }


    @PostMapping("/save")
    public Result save(@RequestBody User user, String smsCode) {
        userService.add(user, smsCode);
        return new Result();
    }


}
