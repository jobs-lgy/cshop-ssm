package com.cshop.controller.goods;

import com.cshop.response.PageResult;
import com.cshop.response.Result;
import com.cshop.domain.Goods;
import com.cshop.entity.Spu;
import com.cshop.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spu")
@CrossOrigin
public class SpuController {

    @Reference
    private SpuService spuService;

    @GetMapping("/findAll")
    public List<Spu> findAll() {
        return spuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Spu> findPage(int page, int size) {
        return spuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Spu> findList(@RequestBody Spu spu) {
        return spuService.findList(spu);
    }

    @PostMapping("/findPage")
    public PageResult<Spu> findPage(@RequestBody Spu spu, int page, int size) {
        return spuService.findPage(spu, page, size);
    }

    @GetMapping("/findById")
    public Spu findById(Long id) {
        return spuService.findById(id);
    }


    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return new Result();
    }


    @GetMapping("/delete")
    public Result delete(Long id) {
        spuService.delete(id);
        return new Result();
    }

    @GetMapping("/logicDelete")
    public Result logicDelete(Long id) {
        spuService.delete(id);
        return new Result();
    }

    @GetMapping("/restore")
    public Result restore(Long id) {
        spuService.restore(id);
        return new Result();
    }

    @GetMapping("/audit")
    public Result audit(Long id) {
        spuService.audit(id);
        return new Result();
    }


    @GetMapping("/pull")
    public Result pull(Long id) {
        spuService.pull(id);
        return new Result();
    }

    @GetMapping("/put")
    public Result put(Long id) {
        spuService.put(id);
        return new Result();
    }

    @GetMapping("/putMany")
    public Result putMany(Long[] ids) {
        int count = spuService.putMany(ids);
        return new Result(0, "上架" + count + "个商品");
    }

    @GetMapping("/findGoodsById")
    public Goods findGoodsById(Long id) {
        return spuService.findGoodsById(id);
    }


}
