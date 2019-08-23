package com.cshop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "tb_spu")
@Data
@DynamicUpdate
public class Spu implements Serializable {
    @Id
    private Long id;//主键
    private String name;//名称
    private String subName;//副标题
    private Long cid1;//一级分类
    private Long cid2;//二级分类
    private Long cid3;//三级分类
    private Long brandId;//品牌ID

    private String description;// 商品描述
    private String packingList;// 包装清单
    private String afterService;// 售后服务

    private String params;//参数列表

    private boolean saleable;//是否上架
    private boolean enable;//是否删除

    @CreationTimestamp
    private java.util.Date createTime;//创建时间
    @UpdateTimestamp
    private java.util.Date updateTime;//更新时间
}
