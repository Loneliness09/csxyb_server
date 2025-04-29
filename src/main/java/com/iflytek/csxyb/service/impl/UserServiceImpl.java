package com.iflytek.csxyb.service.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    public User login(User user) {
        return userDao.select(user);
    }
}
