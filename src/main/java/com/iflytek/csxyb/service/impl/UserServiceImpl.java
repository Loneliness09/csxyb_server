package com.iflytek.csxyb.service.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.UserService;

import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;

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
    public int register(String userName, String loginText, String password) {
        User user = new User();
        if (userName==null || "".equals(userName)) {
            Random random = new Random();
            PrimitiveIterator.OfInt ofInt = random.ints().iterator();
            user.setUserName("User" + Math.abs(ofInt.next()));
        } else {
            user.setUserName(userName);
        }
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
        if (user.getType() == UserType.regular && user.getUserId() != updUser.getUserId() || User.cmpUser(updUser, user)) {
            return 0;
        } else {
            return userDao.update(updUser);
        }
    }

    @Override
    public int updateStatus(User user, User updUser) {
        if (user.getUserId() == updUser.getUserId()) {
            return userDao.updateStatus(updUser);
        } else if (User.cmpUserType(user.getType(), userDao.findUserType(updUser))) {
            return userDao.updateStatus(updUser);
        } else {
            return 0;
        }
    }

    @Override
    public int updateStatus(User user, User updUser, int userStatus) {
        if (user.getUserId() == updUser.getUserId()) {
            return userDao.updateStatus(updUser, userStatus);
        } else if (User.cmpUserType(user.getType(), userDao.findUserType(updUser))) {
            return userDao.updateStatus(updUser, userStatus);
        } else {
            return 0;
        }
    }

    @Override
    public List<User> findUserByName(User user, String userName, int pageNum, int pageSize, UserType type) {
        if (type==UserType.root) {
            return null;
        }
        if (user.getType() != UserType.regular) {
            if (userName == null || "".equals(userName)) {
                return userDao.selectAll(pageNum, pageSize, type);
            } else {
                return userDao.selectByName(userName, pageNum, pageSize, type);
            }
        } else {
            return null;
        }
    }

    @Override
    public int getUserTotalSizeByName(User user, String userName, UserType type) {
        if (type==UserType.root) {
            return 0;
        }
        if (user.getType() != UserType.regular) {
            if (userName == null || "".equals(userName)) {
                return userDao.getTotalSizeAll(type);
            } else {
                return userDao.getTotalSizeByName(userName, type);
            }
        } else {
            return 0;
        }
    }

    @Override
    public User findUserByLoginText(String loginText) {
        return userDao.findUserByLoginText(loginText);
    }
}
