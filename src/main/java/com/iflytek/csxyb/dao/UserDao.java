package com.iflytek.csxyb.dao;

import com.iflytek.csxyb.dao.base.BaseDao;
import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> selectAll(int pageNum, int pageSize);
    int insert(User user);
    int update(User user);
    int delete(User user);
    User loginByText(User user);
    int updateStatus(User user);
}
