package com.iflytek.csxyb.servlet;

import com.google.gson.Gson;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginText = req.getParameter("loginText");
        String password = req.getParameter("password");
        Cookie c = new Cookie("name",loginText);

        User loginUser = new User();
        loginUser.setLoginText(loginText);
        loginUser.setPassword(password);
        UserService userService = new UserServiceImpl();
        User resUser = userService.login(loginUser);
        log.info("进入登录操作，用户名：" + loginText);
        log.error("登录操作结果：" + (resUser != null ? "成功" : "失败"));
        resp.setContentType("application/json");
        Map<String, Object> resData = new HashMap<String, Object>();
        if (resUser != null) {
            resData.put("status", 200);
            resData.put("msg", "操作成功");
            HttpSession session = req.getSession();
            session.setAttribute("loginUser", resUser);
        } else {
            resData.put("status", 500);
            resData.put("msg", "操作失败");
        }
        Gson gson = new Gson();
        resp.addCookie(c);
        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(resData));
        out.flush();
        out.close();

    }
}
