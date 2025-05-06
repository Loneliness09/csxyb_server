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

@WebServlet(name = "UserUpdateServlet", value = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(req.getParameter("userId")); // userId; 用户ID
        String userName = req.getParameter("userName"); // userName; 用户昵称
        String loginText = req.getParameter("loginText"); // loginText; 登录账号
        String password = req.getParameter("password"); // password; 密码
        String phoneNumber = req.getParameter("phoneNumber"); // phoneNumber; 手机号
        String email = req.getParameter("email"); // email; 邮箱
        String wx = req.getParameter("wx"); // wx; 微信号
        UserType type = UserType.valueOf(req.getParameter("type")); // type; 用户类型 {admin, regular, root}
        String remark = req.getParameter("remark"); // geXin; 个性签名
        User updUser = new User();
        updUser.setUserId(userId);
        updUser.setUserName(userName);
        updUser.setLoginText(loginText);
        updUser.setPassword(password);
        updUser.setPhoneNumber(phoneNumber);
        updUser.setEmail(email);
        updUser.setWx(wx);
        updUser.setType(type);
        updUser.setRemark(remark);
        User user = (User) req.getSession().getAttribute("loginUser");
        UserService userService = new UserServiceImpl();
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("修改用户操作结果：失败, 未登录");
        } else {
            int success = userService.updateUser(user, updUser);
            log.info("进入修改操作，修改用户ID：" + userId + " 操作用户ID：" + user.getUserId());
            log.error("修改操作结果：" + (success != 0 ? "成功" : "失败"));
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

