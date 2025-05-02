package com.iflytek.csxyb.servlet.base;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ServletBase {
    public static void writeJsonToResp(HttpServletResponse resp, Map<String, Object> resData) throws IOException {
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(resData));
        out.flush();
        out.close();
    }

    public static void reqSuccess(Map<String, Object> resData) {
        resData.put("status", 200);
        resData.put("msg", "操作成功");
    }

    public static void reqFail(Map<String, Object> resData) {
        resData.put("status", 500);
        resData.put("msg", "操作失败");
    }
}
