package com.iflytek.csxyb.dao;

import com.iflytek.csxyb.dao.base.BaseDao;
import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> selectAll(int pageNum, int pageSize);
    int insert(User user);
    int update(User user);
    int delete(User user);
    UserType findUserType(User user);
    List<User> selectByName(String userName, int pageNum, int pageSize);

    User loginByText(User user);
    int updateStatus(User user);

}
