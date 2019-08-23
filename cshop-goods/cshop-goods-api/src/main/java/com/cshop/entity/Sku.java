package com.cshop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_sku")
@Data
@DynamicUpdate
public class Sku implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spuId;
    private String name;
    private String images; //多个图片用逗号分隔
    private Long price;

    private String indexes;
    private String specs;

    private Boolean enable;// 是否有效，逻辑删除用
    @CreationTimestamp
    private Date createTime;// 创建时间
    @UpdateTimestamp
    private Date updateTime;// 最后修改时间
}
