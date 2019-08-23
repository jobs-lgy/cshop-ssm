package com.cshop.service.impl;

import com.cshop.CacheKey;
import com.cshop.entity.Category;
import com.cshop.repository.BaseRepository;
import com.cshop.repository.CategoryMapper;
import com.cshop.service.AbstractBaseSerivce;
import com.cshop.service.CategoryService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends AbstractBaseSerivce<Category, Long> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseRepository<Category, Long> getRepository() {
        return categoryMapper;
    }

    /**
     * 新增
     *
     * @param category
     */
    public Category add(Category category) {
        Category dbCategory = categoryMapper.save(category);
        saveCategoryTreeToRedis();
        return dbCategory;
    }

    /**
     * 修改
     *
     * @param category
     */
    public Category update(Category category) {
        Category dbCategory = categoryMapper.save(category);
        saveCategoryTreeToRedis();
        return dbCategory;
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        //判断是否存在下级分类
        Category category = new Category();
        category.setParentId(id);
        long count = categoryMapper.count(Example.of(category));
        if (count > 0) {
            throw new RuntimeException("存在下级分类不能删除");
        }
        categoryMapper.deleteById(id);
        saveCategoryTreeToRedis();
    }

    public List<Map> findCategoryTree() {
        //从缓存中查询
        return (List<Map>) redisTemplate.boundValueOps(CacheKey.CATEGORY_TREE).get();
    }


    public void saveCategoryTreeToRedis() {
        System.out.println("加载商品分类数据到缓存");
        Category category = new Category();
        List<Category> categories = categoryMapper.findAll(Example.of(category));
        List<Map> categoryTree = findByParentId(categories, 0L);
        redisTemplate.boundValueOps(CacheKey.CATEGORY_TREE).set(categoryTree);
    }


    private List<Map> findByParentId(List<Category> categoryList, Long parentId) {
        List<Map> mapList = new ArrayList<Map>();
        for (Category category : categoryList) {
            if (category.getParentId().equals(parentId)) {
                Map map = new HashMap();
                map.put("name", category.getName());
                map.put("menus", findByParentId(categoryList, category.getId()));
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public Predicate buildPageParams(Root<Category> root, Category entity, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(entity.getName())) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + entity.getName() + "%"));
        }

        if (!StringUtils.isEmpty(entity.getParentId())) {
            predicates.add(cb.equal(root.get("parentId").as(String.class), entity.getParentId()));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }
}
