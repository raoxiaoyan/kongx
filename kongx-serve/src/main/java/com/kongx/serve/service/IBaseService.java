package com.kongx.serve.service;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public interface IBaseService<T, PK> {
    default PaginationSupport findByPage(T entity) {
        return new PaginationSupport();
    }

    default List<T> findAll(T entity) {
        return new ArrayList<>();
    }

    void add(T entity, UserInfo userInfo);

    default void remove(int pk) {
    }

    void update(T entity, UserInfo userInfo);

    T findById(PK id);
}
