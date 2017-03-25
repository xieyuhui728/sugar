package com.web.mvc.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by xieyuhui on 17-3-25.
 */
@Repository
public interface BaseDao<T> {
    public T selectOne(T t);
}
