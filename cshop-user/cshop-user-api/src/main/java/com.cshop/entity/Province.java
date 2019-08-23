package com.cshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * provinces实体类
 *
 * @author Administrator
 */
@Data
@Entity
@Table(name = "tb_province")
public class Province implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//省份ID
    private String name;//省份名称

}
