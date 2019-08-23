package com.cshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_category_brand")
@Data
@IdClass(CategoryBrand.CategoryBrandPk.class)
public class CategoryBrand implements Serializable {
    @Id
    private Long categoryId;
    @Id
    private Long brandId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryBrandPk implements Serializable {
        private Long categoryId;
        private Long brandId;
    }
}
