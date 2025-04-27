package com.iflytek.csxyb.test;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.dao.impl.UserDaoImpl;
import com.iflytek.csxyb.entity.User;
import org.junit.Test;

public class UserTest {
    UserDao userDao = new UserDaoImpl();
    @Test
    public void loginTest() {
        String loginText = "twq";
        String password = "123";
        User user = new User(loginText, password);
        user = userDao.login(user);
        System.out.println(user);
    }
}
