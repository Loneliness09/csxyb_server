package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;

import java.util.List;

public interface UserService {
    User loginByText(String loginText, String password);
    int register(User user);

    int register(String userName, String loginText, String password);

    int unRegister(User user, User delUser);
    int updateUser(User user, User updUser);
    List<User> findUserByName(User user, String userName, int pageNum, int pageSize, UserType type);
    User findUserByLoginText(String loginText);
    int getUserTotalSizeByName(User user, String userName, UserType type);
    int updateStatus(User user, User updUser);
    int updateStatus(User user, User updUser, int userStatus);
}
