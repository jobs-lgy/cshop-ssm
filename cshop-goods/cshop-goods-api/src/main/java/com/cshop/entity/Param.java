package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_param")
@Data
public class Param implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//ID
    private Long categoryId;
    private String groupName;//名称
    private String name;//名称
    private String options;//规格选项
    private boolean searchable;
    private boolean numerical;
}
