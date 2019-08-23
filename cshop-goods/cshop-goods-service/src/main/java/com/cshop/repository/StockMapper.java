package com.cshop.repository;

import com.cshop.entity.Stock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper extends BaseRepository<Stock, Long> {
}
