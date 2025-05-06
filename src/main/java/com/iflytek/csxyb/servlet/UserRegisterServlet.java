package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import com.iflytek.csxyb.servlet.base.ServletBase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserRegisterServlet", value = "/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String loginText = req.getParameter("loginText");
        String password = req.getParameter("password");
        UserService userService = new UserServiceImpl();
        int success = userService.register(loginText, password);
        log.info("进入注册操作，用户名：" + loginText);
        log.error("登录操作结果：" + (success != 0 ? "成功" : "失败"));
        resp.setContentType("application/json");
        Map<String, Object> resData = new HashMap<String, Object>();
        if (success != 0) {
            ServletBase.reqSuccess(resData);
        } else {
            ServletBase.reqFail(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
