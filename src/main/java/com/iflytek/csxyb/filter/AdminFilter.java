package com.iflytek.csxyb.filter;

import com.google.gson.Gson;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "AdminFilter", value = "/FindUserServlet")
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser != null && loginUser.getType() != UserType.regular) { // 合法管理员
            chain.doFilter(request, response);
        } else {
            response.setContentType("application/json");
            Map<String, Object> resData = new HashMap<String, Object>();
            resData.put("status", 500);
            resData.put("msg", "非管理员");
            Gson gson = new Gson();
            PrintWriter out = response.getWriter();
            out.println(gson.toJson(resData));
            out.flush();
            out.close();
        }
    }
}
