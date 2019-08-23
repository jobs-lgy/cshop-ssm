package com.cshop.service;


import com.cshop.response.PageResult;

import java.util.List;

/**
 * 业务逻辑层
 */
public interface BaseService<T, ID> {

    public List<T> findAll();

    public PageResult<T> findPage(int page, int size);


    public List<T> findList(T t);


    public PageResult<T> findPage(T t, int page, int size);


    public T findById(ID id);

    public T add(T t);

    public T update(T t);

    public void delete(ID id);

}
