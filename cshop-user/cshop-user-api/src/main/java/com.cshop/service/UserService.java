package com.cshop.service;

import com.cshop.entity.User;

/**
 * user业务逻辑层
 */
public interface UserService extends BaseService<User, Long> {
    /**
     * 发送短信验证码
     *
     * @param phone
     */
    public void sendSms(String phone);


    /**
     * 增加
     *
     * @param user
     * @param smsCode
     */
    public void add(User user, String smsCode);
}
