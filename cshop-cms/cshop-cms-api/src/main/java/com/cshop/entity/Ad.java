package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_ad")
@Data
public class Ad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//ID

    private String name;//广告名称

    private String position;//广告位置

    private java.util.Date startTime;//开始时间

    private java.util.Date endTime;//到期时间

    private String status;//状态

    private String image;//图片地址

    private String url;//URL

    private String remarks;//备注
}
