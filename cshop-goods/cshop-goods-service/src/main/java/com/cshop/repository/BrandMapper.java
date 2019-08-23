package com.cshop.repository;

import com.cshop.entity.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BrandMapper extends BaseRepository<Brand, Long> {

    /**
     * 根据分类名称查询品牌列表
     *
     * @param categoryName
     * @return
     */
    @Query(value = "SELECT name,image FROM tb_brand WHERE id  IN (SELECT brand_id FROM tb_category_brand WHERE  category_id IN (SELECT id FROM tb_category WHERE NAME=:categoryName) )order by seq", nativeQuery = true)
    public List<Map> findAllByCategoryName(@Param("categoryName") String categoryName);

}
