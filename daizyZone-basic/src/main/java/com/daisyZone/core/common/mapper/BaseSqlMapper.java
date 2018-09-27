package com.daisyZone.core.common.mapper;

import java.util.List;

/**
 * 通用方法
 *
 * @param <T>
 * @author jiaqi 2016年6月6日
 * @see
 * @since 1.0
 */
public interface BaseSqlMapper<T> extends SqlMapper {

    void add(T data);

    void update(T data);

    void delete(T data);

    List<T> query(T condition);

    Integer count(T condition);

    T find(Long id);
}