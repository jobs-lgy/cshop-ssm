package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * admin实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_admin")
@Data
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//id

    private String loginName;//用户名

    private String password;//密码

    private String status;//状态

}
