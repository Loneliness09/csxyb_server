package com.iflytek.csxyb.dao.base;

public interface BaseDao<T> {
    T select(T o);
    int insert(T o);
    int update(T o);
    int delete(T o);

}
