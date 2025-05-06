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

@WebServlet(name = "GetUserTotalSizeServlet", value = "/GetUserTotalSizeServlet")
public class GetUserTotalSizeServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("loginUser");
        resp.setContentType("application/json");
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("查询商品总数操作结果：失败, 未登录");
        } else {
            log.info("进入查询商品总数操作，用户名：" + user.getUserName());
            UserService userService = new UserServiceImpl();
            String name = req.getParameter("userName");
            UserType type = UserType.valueOf(req.getParameter("userType"));
            int totalSize = userService.getUserTotalSizeByName(user, name, type);
            log.error("查询商品总数操作结果：" + (totalSize != 0 ? "成功" : "失败"));
            resData.put("totalSize", String.valueOf(totalSize));
            ServletBase.reqSuccess(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}