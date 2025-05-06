package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.GoodsService;
import com.iflytek.csxyb.service.impl.GoodsServiceImpl;
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
@WebServlet(name = "FindGoodsServlet", value = "/FindGoodsServlet")
public class FindGoodsServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * @Method: doPost
     * @Description: 查找商品
     * @Anthor: Jarvis Sun
     *
     * @param req: HttpServletRequest
     * @param resp: HttpServletResponse
     * req.Param:
     * goodsName: 查询商品名称(模糊查询, 为空即查询全部)
     * pageSize: 每页数据量
     * pageNum: 页数(从1开始)
     * @return:
     * resp.data: List<Goods>
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("loginUser");
        resp.setContentType("application/json");
        Map<String, Object> resData = new HashMap<>();
        if (user == null) {
            ServletBase.reqFail(resData);
            log.error("查询商品操作结果：失败, 未登录");
        } else {
            log.info("进入查询商品操作，用户名：" + user.getUserName());
            GoodsService goodsService = new GoodsServiceImpl();
            String name = req.getParameter("goodsName");
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            int pageNum = Integer.parseInt(req.getParameter("pageNum"));
            List<Goods> goodsList = goodsService.findGoodsByName(user, name, pageNum, pageSize);
            log.error("查询商品操作结果：" + (goodsList != null ? "成功" : "失败"));
            resData.put("data", goodsList);
            ServletBase.reqSuccess(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
