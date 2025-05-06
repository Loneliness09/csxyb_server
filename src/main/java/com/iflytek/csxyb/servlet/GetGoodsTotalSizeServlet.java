package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.service.GoodsService;
import com.iflytek.csxyb.service.impl.GoodsServiceImpl;
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

@WebServlet(name = "GetGoodsTotalSizeServlet", value = "/GetGoodsTotalSizeServlet")
public class GetGoodsTotalSizeServlet extends HttpServlet {
    private final Logger log = LogManager.getRootLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * @Method: doPost
     * @Description: 获取查找商品的总数
     * @Anthor: Jarvis Sun
     *
     * @param req: HttpServletRequest
     * @param resp: HttpServletResponse
     * req.Param:
     * goodsName: 查询商品名称(模糊查询, 为空即查询全部)
     * @return:
     * resp.totalSize: int
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
            log.error("查询商品总数操作结果：失败, 未登录");
        } else {
            log.info("进入查询商品总数操作，用户名：" + user.getUserName());
            GoodsService goodsService = new GoodsServiceImpl();
            String name = req.getParameter("goodsName");
            int totalSize = goodsService.getGoodsTotalSizeByName(user, name);
            log.error("查询商品总数操作结果：" + (totalSize != 0 ? "成功" : "失败"));
            resData.put("totalSize", String.valueOf(totalSize));
            ServletBase.reqSuccess(resData);
        }
        ServletBase.writeJsonToResp(resp, resData);
    }
}
