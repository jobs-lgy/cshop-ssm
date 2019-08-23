package com.cshop.service;

import com.cshop.response.PageResult;
import com.cshop.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractBaseSerivce<T, ID> {

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public PageResult<T> findPage(int page, int size) {
        Page result = getRepository().findAll(PageRequest.of(page - 1, size));
        return new PageResult<T>(result.getTotalElements(), result.getContent());
    }

    /**
     * 条件查询
     *
     * @return
     */
    public List<T> findList(T t) {
        Specification<T> specification = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return buildPageParams(root, t, cb);
            }
        };
        return getRepository().findAll(specification);
    }

    /**
     * 分页+条件查询
     *
     * @param page
     * @param size
     * @return
     */
    public PageResult<T> findPage(T t, int page, int size) {
        Specification<T> specification = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return buildPageParams(root, t, cb);
            }
        };
        Page result = getRepository().findAll(specification, PageRequest.of(page - 1, size));
        return new PageResult<T>(result.getTotalElements(), result.getContent());
    }

    public abstract Predicate buildPageParams(Root<T> root, T t, CriteriaBuilder cb);

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    public T findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 新增
     *
     * @param t
     */
    public T add(T t) {
        return getRepository().save(t);
    }

    /**
     * 修改
     *
     * @param t
     */
    public T update(T t) {
        return getRepository().saveAndFlush(t);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    public abstract BaseRepository<T, ID> getRepository();
}

