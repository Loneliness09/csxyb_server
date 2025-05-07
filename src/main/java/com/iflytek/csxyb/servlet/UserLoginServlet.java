package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import com.iflytek.csxyb.servlet.base.ServletBase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * @Method: doPost
     * @Description: 用户登录
     * @Anthor: Jarvis Sun
     *
     * @param req: HttpServletRequest
     * @param resp: HttpServletResponse
     * req.Param:
     *            loginText: 用户登录名
     *            password: 密码
     * @return:
     * resp.status: 200
     * resp.loginUser: User
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String loginText = req.getParameter("loginText");
        String password = req.getParameter("password");
        UserService userService = new UserServiceImpl();
        User resUser = userService.loginByText(loginText, password);
        log.info("进入登录操作，用户名：" + loginText);
        log.error("登录操作结果：" + (resUser != null ? "成功" : "失败"));
        resp.setContentType("application/json");
        Map<String, Object> resData = new HashMap<>();
        if (resUser != null) {
            ServletBase.reqSuccess(resData);
            req.getSession().setAttribute("loginUser", resUser);
            Cookie c = new Cookie("name", resUser.getUserName());
            resp.addCookie(c);
            resData.put("loginUser", resUser);
        } else {
            ServletBase.reqFail(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
