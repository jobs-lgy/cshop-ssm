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
@Table(name = "tb_spec")
@Data
public class Spec implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//ID
    private Long categoryId;

    private String name;//名称
    private String options;//规格选项
}
