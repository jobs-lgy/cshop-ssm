package com.cshop.timer;

import com.cshop.entity.SeckillGoods;
import com.cshop.service.SeckillGoodsService;
import com.cshop.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class SeckillGoodsTask {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Scheduled(cron = "0/15 * * * * ?")
    public void loadGoods() {
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date startTime : dateMenus) {
            Set keys = redisTemplate.boundHashOps("SeckillGoods_" + DateUtil.date2Str(startTime)).keys();
            if (keys != null && keys.size() > 0) {
            }
            SeckillGoods seckillGoods = new SeckillGoods();
            List<SeckillGoods> seckillGoodsList = seckillGoodsService.findList(seckillGoods);
            for (SeckillGoods seckillGood : seckillGoodsList) {
                redisTemplate.boundHashOps("SeckillGoods_" + DateUtil.date2Str(startTime)).put(seckillGood.getId(), seckillGood);
            }
        }
    }
}
