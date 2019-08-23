package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_activity")
@Data
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//ID

    private String title;//活动标题

    private java.util.Date startTime;//开始时间

    private java.util.Date endTime;//结束时间

    private String status;//状态

    private String content;//活动内容

}
