package com.cshop.repository;

import com.cshop.entity.Sku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuMapper extends BaseRepository<Sku, Long> {


    /**
     * 更改库存数
     *
     * @param id
     * @param changeNum
     * @return
     */
    @Query(value = "update tb_sku set num=num+ :changeNum where id= :id", nativeQuery = true)
    public void updateNum(@Param("id") Long id, @Param("changeNum") Integer changeNum);

    @Query(value = "delete from tb_sku where spu_id= :spuId", nativeQuery = true)
    void deleteBySpuId(Long spuId);
}
