package com.daisyZone.core.common.service;


import java.util.List;

/**
 * 通用service层接口,该接口包含增删改查等基本方法,实现该接口使编程更加统一规范
 *
 * @param <T> 增删改查操作对象
 */
public interface BaseCommonService<T> {
//    PageData<T> pageQuery(PageQuery pageQuery);

    T add(T data);

    T update(T data);

    void delete(T data);

    List<T> query(T condition);

    Integer count(T condition);

    T find(Long id);
}
