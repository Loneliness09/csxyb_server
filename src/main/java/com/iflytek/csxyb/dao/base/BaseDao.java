package com.iflytek.csxyb.dao.base;

import java.util.List;

public interface BaseDao<T> {
    List<T> selectAll(int pageNum, int pageSize);
    int insert(T o);
    int update(T o);
    int delete(T o);
}
