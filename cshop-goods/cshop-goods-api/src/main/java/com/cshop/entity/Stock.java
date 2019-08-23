package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "tb_stock")
@Entity
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skuId;
    private Integer seckillNum;// 可秒杀库存
    private Integer seckillTotal;
    private Integer num;// 正常库存
}