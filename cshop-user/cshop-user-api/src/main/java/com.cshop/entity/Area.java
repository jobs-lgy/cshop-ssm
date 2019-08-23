package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * areas实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_areas")
public class Area implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//区域ID

    private String name;//区域名称

    private Long cityId;//城市ID
}
