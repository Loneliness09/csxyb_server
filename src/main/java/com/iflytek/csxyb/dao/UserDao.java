package com.iflytek.csxyb.dao;

import com.iflytek.csxyb.dao.base.BaseDao;
import com.iflytek.csxyb.entity.User;

public interface UserDao extends BaseDao<User> {
    User select(User user);
    int insert(User user);
    int update(User user);
    int delete(User user);
}
