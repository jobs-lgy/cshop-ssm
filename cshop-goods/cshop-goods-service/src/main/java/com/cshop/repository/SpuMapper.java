package com.cshop.repository;

import com.cshop.entity.Spu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpuMapper extends BaseRepository<Spu, Long> {

    List<Spu> findAllByIdInAndSaleable(List<Long> asList, boolean saleable);
}
