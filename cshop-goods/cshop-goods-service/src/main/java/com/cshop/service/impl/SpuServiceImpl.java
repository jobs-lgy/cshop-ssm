package com.cshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cshop.util.IdWorker;
import com.cshop.domain.Goods;
import com.cshop.entity.Category;
import com.cshop.entity.CategoryBrand;
import com.cshop.entity.Sku;
import com.cshop.entity.Spu;
import com.cshop.repository.*;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.SkuSearchService;
import com.cshop.service.SkuService;
import com.cshop.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Transactional
public class SpuServiceImpl extends AbstractBaseSerivce<Spu, Long> implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private CategoryBrandMapper categoryBrandMapper;


    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuSearchService skuSearchService;

    @Override
    public BaseRepository<Spu, Long> getRepository() {
        return spuMapper;
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        spuMapper.deleteById(id);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void logicDelete(Long id) {
        Spu spu = findById(id);
        spu.setEnable(false);//删除
        spuMapper.save(spu);
    }

    /**
     * 恢复数据
     *
     * @param id
     */
    public void restore(Long id) {
        Spu spu = findById(id);

        spu.setSaleable(false);//未删除
        spuMapper.save(spu);
    }

    /**
     * 审核
     *
     * @param id
     */
    public void audit(Long id) {
        Spu spu = findById(id);
        spu.setSaleable(true);//审核后自动上架
        spuMapper.save(spu);
    }

    /**
     * 下架商品
     *
     * @param id
     */
    public void pull(Long id) {
        Spu spu = findById(id);
        spu.setSaleable(false);//下架状态
        spuMapper.saveAndFlush(spu);
    }

    /**
     * 上架商品
     *
     * @param id
     */
    public void put(Long id) {
        Spu spu = findById(id);
        spu.setSaleable(true);//上架状态
        spuMapper.save(spu);
    }


    /**
     * 批量上架商品
     *
     * @param ids
     */
    public int putMany(Long[] ids) {
        Spu spu = new Spu();
        spu.setSaleable(true);//上架

        List<Spu> spuList = spuMapper.findAllByIdInAndSaleable(Arrays.asList(ids), true);
        spuList.stream().forEach(t -> {
            t.setSaleable(false);
        });

        return spuMapper.saveAll(spuList).size();
    }


    /**
     * 保存商品 SPU+SKU列表
     *
     * @param goods 商品组合实体类
     */
    public void saveGoods(Goods goods) {
        boolean isAdd = true;//是否新增
        //取出spu部分
        Spu spu = goods.getSpu();
        if (spu.getId() == null) {  //新增
            spu.setId(idWorker.nextId());//设置ID
            System.out.println("新增");
        } else {//如果修改，删除原SKU列表
            isAdd = false;
            //删除原sku列表
            skuMapper.deleteBySpuId(spu.getId());
            System.out.println("修改");
        }
        Date date = new Date();//获取当前日期
        //根据商品分类ID查询商品名称
        Category category = categoryMapper.findById(spu.getCid3()).orElse(null);
        //取出sku列表部分
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {
            if (sku.getId() == null) {  //新增的SKU
                sku.setId(idWorker.nextId());//分布式ID
                sku.setCreateTime(date);//创建日期
            }
            //构建SKU名称，采用SPU+规格值组装
            if (sku.getSpecs() == null || "".equals(sku.getSpecs())) {
                sku.setSpecs("{}");
            }
            String name = spu.getName();
            Map<String, String> specMap = JSON.parseObject(sku.getSpecs(), Map.class);//规格
            for (String value : specMap.values()) {
                name += " " + value;
            }
            sku.setName(name);//名称
            sku.setSpuId(spu.getId());//设置spu的ID
            sku.setUpdateTime(date);//修改日期
            skuMapper.save(sku);//插入sku表数据


            skuService.savePriceToRedisBySkuId(sku.getId(), sku.getPrice());//更新价格到redis
        }

        skuSearchService.importSkuList(skuList);

        spu.setEnable(false);//是否删除
        spu.setSaleable(false); //上架状态
        spuMapper.save(spu);//保存spu

        //添加 模板品牌关联
        //先查询是否存在记录
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(spu.getBrandId());
        categoryBrand.setCategoryId(spu.getCid3());
        Long count = categoryBrandMapper.count(Example.of(categoryBrand));
        if (count == 0) {
            categoryBrandMapper.save(categoryBrand);
        }
    }


    /**
     * 根据ID查询商品
     *
     * @param id
     * @return
     */
    public Goods findGoodsById(Long id) {
        //查询spu
        Spu spu = findById(id);
        //查询SKU 列表
        Sku temp = new Sku();
        temp.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.findAll(Example.of(temp));

        //封装，返回
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public Predicate buildPageParams(Root<Spu> root, Spu entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getSubName())) {
            predicates.add(cb.like(root.get("subName").as(String.class), "%" + entity.getSubName() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
