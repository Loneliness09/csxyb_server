package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import com.iflytek.csxyb.servlet.base.ServletBase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserUnRegisterServlet", value = "/UserUnRegisterServlet")
public class UserUnRegisterServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = (User) req.getSession().getAttribute("loginUser");
        UserService userService = new UserServiceImpl();
        User delUser = new User();
        delUser.setUserId(userId);
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("注销用户操作结果：失败, 未登录");
        } else if (user.getType() == UserType.regular) {
            ServletBase.reqFail(resData);
            log.error("注销用户操作结果：失败, 用户权限不足");
        } else {
            int success = userService.unRegister(user, delUser);
            log.info("进入注销操作，注销用户ID：" + userId + " 操作用户ID：" + user.getUserId());
            log.error("注销操作结果：" + (success != 0 ? "成功" : "失败"));
            resp.setContentType("application/json");
            if (success != 0) {
                ServletBase.reqSuccess(resData);
            } else {
                ServletBase.reqFail(resData);
            }
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
