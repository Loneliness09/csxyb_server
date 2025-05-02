package com.iflytek.csxyb.servlet;

import com.google.gson.Gson;
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
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet(name = "FindGoodsServlet", value = "/FindGoodsServlet")
public class FindGoodsServlet extends HttpServlet {
    private Logger log = LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("loginUser");
        Map<String, Object> resData = new HashMap<String, Object>();
        if (user == null) {
            ServletBase.reqFail(resData);
        } else {
            GoodsService goodsService = new GoodsServiceImpl();
            String name = req.getParameter("goodsName");
            int pageSize = Integer.valueOf(req.getParameter("pageSize"));
            int pageNum = Integer.valueOf(req.getParameter("pageNum"));
            List<Goods> goodsList = goodsService.findGoodsByName(user, name, pageNum, pageSize);

        }

    }
}
