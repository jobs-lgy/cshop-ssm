package com.cshop.domain;

import com.cshop.entity.Sku;
import com.cshop.entity.Spu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品组合实体类
 */
@Data
public class Goods implements Serializable {
    private Spu spu;
    private List<Sku> skuList;
}
