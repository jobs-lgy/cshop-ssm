package com.cshop.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cart implements Serializable {
    private Long userId;
    private Long skuId;
    private String name;
    private String images;
    private Long price;
    private Integer num;
    private String specs;
}
