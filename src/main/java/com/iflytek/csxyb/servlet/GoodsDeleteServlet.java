package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.GoodsService;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.GoodsServiceImpl;
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

@WebServlet(name = "GoodsDeleteServlet", value = "/GoodsDeleteServlet")
public class GoodsDeleteServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        int goodsId = Integer.parseInt(req.getParameter("goodsId"));
        User user = (User) req.getSession().getAttribute("loginUser");
        GoodsService goodsService = new GoodsServiceImpl();
        Goods delGoods = new Goods();
        delGoods.setGoodsId(goodsId);
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("删除商品操作结果：失败, 未登录");
        } else {
            int success = goodsService.deleteGoods(user, delGoods);
            log.info("进入删除商品操作，删除商品ID：" + goodsId + " 操作用户ID：" + user.getUserId());
            log.error("删除商品操作结果：" + (success != 0 ? "成功" : "失败"));
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