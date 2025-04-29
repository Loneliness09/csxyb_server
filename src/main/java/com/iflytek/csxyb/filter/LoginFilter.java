package com.iflytek.csxyb.filter;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "LoginFilter", value = "/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri.contains("/LoginServlet") || uri.contains(".jsp")){
            chain.doFilter(request, response);
        }else {
            Object loginUser = req.getSession().getAttribute("loginUser");
            if (loginUser!=null){//合法用户
                chain.doFilter(request, response);
            }else {
                response.setContentType("application/json");
                Map<String, Object> resData = new HashMap<String, Object>();
                resData.put("status", 500);
                resData.put("msg", "未登录");
                Gson gson = new Gson();
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(resData));
                out.flush();
                out.close();
            }
        }
    }
}
