package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * menu实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_menu")
@Data
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//菜单ID

    private String name;//菜单名称

    private String icon;//图标

    private String url;//URL

    private Long parentId;//上级菜单ID
}
