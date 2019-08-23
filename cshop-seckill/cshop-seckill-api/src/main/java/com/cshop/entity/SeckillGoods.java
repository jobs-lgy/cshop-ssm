package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_seckill_goods")
@Data
public class SeckillGoods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * sku ID
     */
    private Long skuId;

    /**
     * 标题
     */
    private String name;

    /**
     * 商品图片
     */
    private String images;

    /**
     * 原价格
     */
    private Long price;

    /**
     * 秒杀价格
     */
    private Long seckillPrice;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private Date createTime;

    private Date updateTime;

}