package com.iflytek.csxyb.service.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User loginByText(String loginText, String password) {
        User user = new User();
        user.setLoginText(loginText);
        user.setPassword(password);
        return userDao.loginByText(user);
    }

    @Override
    public int register(User user) {
        return userDao.insert(user);
    }

    @Override
    public int unRegister(User user) {
        return userDao.delete(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.update(user);
    }

}
