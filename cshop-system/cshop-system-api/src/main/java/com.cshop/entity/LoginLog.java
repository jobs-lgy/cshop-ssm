package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * loginLog实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_login_log")
@Data
public class LoginLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//id

    private String ip;//ip

    private String browserName;
    private String loginName;
    private String location;//地区

    private java.util.Date loginTime;//登录时间
}
