package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * cities实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//城市ID

    private String name;//城市名称

    private Long provinceId;//省份ID
}
