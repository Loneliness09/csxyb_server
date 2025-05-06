package com.iflytek.csxyb.service.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.UserService;

import java.util.List;

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
    public int register(String loginText, String password) {
        User user = new User();
        user.setLoginText(loginText);
        user.setPassword(password);
        return userDao.insert(user);
    }

    @Override
    public int unRegister(User user, User delUser) {
        if (user.getType() == UserType.regular && user.getUserId() != delUser.getUserId()) {
            return 0;
        } else {
            delUser.setType(userDao.findUserType(delUser));
            if (User.cmpUser(user, delUser)) {
                return userDao.delete(delUser);
            } else {
                return 0;
            }
        }
    }

    @Override
    public int updateUser(User user, User updUser) {
        if (user.getType() == UserType.regular && user.getUserId() != updUser.getUserId()) {
            return 0;
        } else {
            return userDao.update(updUser);
        }
    }

    @Override
    public List<User> findUserByName(User user, String userName, int pageNum, int pageSize) {
        if (user.getType() != UserType.regular) {
            if (userName == null || "".equals(userName)) {
                return  userDao.selectAll(pageNum, pageSize);
            } else {
                return userDao.selectByName(userName, pageNum, pageSize);
            }
        } else {
            return null;
        }
    }

}
