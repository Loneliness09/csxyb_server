package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.User;

public interface UserService {
    User loginByText(String loginText, String password);
    int register(User user);
    int unRegister(User user);
    int updateUser(User user);
}
