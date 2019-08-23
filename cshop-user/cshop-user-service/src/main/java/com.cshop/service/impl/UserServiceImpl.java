package com.cshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cshop.entity.User;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.UserMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends AbstractBaseSerivce<User, Long> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public BaseRepository<User, Long> getRepository() {
        return userMapper;
    }

    @Override
    public Predicate buildPageParams(Root<User> root, User entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getUsername())) {
            predicates.add(cb.like(root.get("username").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getNickName())) {
            predicates.add(cb.like(root.get("nickName").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getPhone())) {
            predicates.add(cb.like(root.get("phone").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getEmail())) {
            predicates.add(cb.like(root.get("email").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getSourceType())) {
            predicates.add(cb.like(root.get("sourceType").as(String.class), "%" + entity.getName() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    public void sendSms(String phone) {
        //1.得到六位短信验证码
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int code = random.nextInt(max);
        if (code < min) {
            code = code + min;
        }
        System.out.println("短信验证码：" + code);
        //2.保存到redis里
        redisTemplate.boundValueOps("code_" + phone).set(code + "");
        redisTemplate.boundValueOps("code_" + phone).expire(5, TimeUnit.MINUTES);//5分钟失效
        //3.发送给RabbitMQ
        Map<String, String> map = new HashMap();
        map.put("phone", phone);
        map.put("code", code + "");
        rabbitTemplate.convertAndSend("exchange.sms", "sms", JSON.toJSONString(map));
    }

    /**
     * 增加
     *
     * @param user
     * @param smsCode
     */
    public void add(User user, String smsCode) {
        //比较短信验证码
        //获取系统短信验证码
        String sysCode = (String) redisTemplate.boundValueOps("code_" + user.getPhone()).get();
        if (sysCode == null) {
            throw new RuntimeException("验证码未发送或已过期");
        }
        if (!smsCode.equals(sysCode)) {
            throw new RuntimeException("验证码不正确");
        }
        if (user.getUsername() == null) {
            user.setUsername(user.getPhone());
        }
        User searchUser = new User();
        searchUser.setUsername(user.getUsername());
        if (userMapper.count(Example.of(searchUser)) > 0) {  //查询是否存在相同记录
            throw new RuntimeException("该手机号已注册");
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPoints(0);//积分初始值为0
        user.setStatus("1");//状态1
        user.setIsEmailCheck("0");//邮箱认证
        user.setIsMobileCheck("1");//手机认证

        String password = DigestUtils.md5Hex(user.getPassword());//对密码加密
        user.setPassword(password);
        userMapper.save(user);
    }

}
