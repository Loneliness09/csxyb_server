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

@WebServlet(name = "UserStatusUpdateServlet", value = "/UserStatusUpdateServlet")
public class UserStatusUpdateServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * @Method: doPost
     * @Description: 修改用户状态
     * @Anthor: Jarvis Sun
     *
     * @param req: HttpServletRequest
     * @param resp: HttpServletResponse
     * req.Param:
     * userId: 用户ID
     * @return:
     * resp.status: 200
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(req.getParameter("userId"));
        User user = (User) req.getSession().getAttribute("loginUser");
        UserService userService = new UserServiceImpl();
        User updUser = new User();
        updUser.setUserId(userId);
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("修改用户状态用户操作结果：失败, 未登录");
        } else if (user.getType() == UserType.regular) {
            ServletBase.reqFail(resData);
            log.error("修改用户状态操作结果：失败, 用户权限不足");
        } else {
            int success = userService.updateStatus(user, updUser);
            log.info("进入修改用户状态操作，注销用户ID：" + userId + " 操作用户ID：" + user.getUserId());
            log.error("修改用户状态操作结果：" + (success != 0 ? "成功" : "失败"));
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
