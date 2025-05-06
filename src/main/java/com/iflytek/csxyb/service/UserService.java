package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface UserService {
    User loginByText(String loginText, String password);
    int register(User user);

    int register(String loginText, String password);

    int unRegister(User user, User delUser);
    int updateUser(User user, User updUser);
    List<User> findUserByName(User user, String userName, int pageNum, int pageSize);
}
