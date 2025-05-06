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
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "FindUserServlet", value = "/FindUserServlet")
public class FindUserServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();

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
            log.error("查询用户操作结果：失败, 未登录");
        } else if (user.getType() == UserType.regular) {
            ServletBase.reqFail(resData);
            log.error("查询用户操作结果：失败, 用户权限不足");
        } else {
            log.info("进入查询用户操作，用户名：" + user.getUserName());
            UserService userService = new UserServiceImpl();
            String name = req.getParameter("userName");
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            int pageNum = Integer.parseInt(req.getParameter("pageNum"));
            List<User> userList = userService.findUserByName(user, name, pageNum, pageSize);
            log.error("查询用户操作结果：" + (userList != null ? "成功" : "失败, 未找到指定用户"));
            resData.put("data", userList);
            ServletBase.reqSuccess(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
