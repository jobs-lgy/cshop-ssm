package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_category")
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//分类ID
    private String name;//分类名称
    private Long parentId;//上级ID
    private Boolean isParent;
    private Integer sort;//排序
}
