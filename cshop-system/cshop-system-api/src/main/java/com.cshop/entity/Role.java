package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * role实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_role")
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//ID

    private String name;//角色名称
}
