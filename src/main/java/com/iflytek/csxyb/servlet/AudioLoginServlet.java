package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.Audio;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.UserService;
import com.iflytek.csxyb.service.impl.UserServiceImpl;
import com.iflytek.csxyb.servlet.base.ServletBase;
import com.iflytek.csxyb.utils.audio.api.CreateFeature;
import com.iflytek.csxyb.utils.audio.api.SearchOneFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AudioLoginServlet", value = "/AudioLoginServlet")  // 映射到 /api/upload
@MultipartConfig  // 启用多部分表单处理
public class AudioLoginServlet extends HttpServlet {
    private static String requestUrl = "https://api.xf-yun.com/v1/private/s782b4996";

    //控制台获取以下信息
    private static String APPID = "01b7d702";
    private static String apiSecret = "ODI3ZjNiZWQyNWUzMjM2NWUxNDllODIw";
    private static String apiKey = "de8ee7262fec1af8c18c587ce1d77e9b";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setContentType("text/plain");  // 设置响应内容类型
        String loginText = req.getParameter("loginText");
        System.out.println(loginText);
        resp.setCharacterEncoding("UTF-8");  // 设置字符编码
        UserService userService = new UserServiceImpl();
        User user = userService.findUserByLoginText(loginText);
        resp.setContentType("application/json");
        try {
            // 获取上传的文件部分
            Part filePart = req.getPart("file");  // "file" 是表单字段名
            System.out.println("文件上传中" + filePart);

            // 检查文件是否为空
            if (filePart == null || filePart.getSize() == 0) {
                resp.getWriter().write("文件为空，请选择一个有效的音频文件");
                return;
            }

            // 检查文件类型是否为音频文件
            String contentType = filePart.getContentType();
            if (!contentType.startsWith("audio/")) {
                resp.getWriter().write("只允许上传音频文件");
                return;
            }

            // 定义文件存储路径
            String uploadDir = "D:\\uploadAudio\\loginAudio";
            File uploadPath = new File(uploadDir);

            // 如果目录不存在，则创建目录
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            // 构造文件保存路径
//            String originalFileName = getFileNameFromPart(filePart);  // 提取文件名
            String tempFilePath = uploadDir + File.separator + "temp_" + user.getUserId() + ".mp3";
            String convertedFilePath = uploadDir + File.separator + "loginAudioKey_" + user.getUserId() + ".mp3";

            File tempFile = new File(tempFilePath);

            // 将上传的文件保存到临时路径
            filePart.write(tempFile.getAbsolutePath());  // 保存文件

            // 调用转换方法
            Audio.mp3Converter(tempFile.getAbsolutePath(), convertedFilePath);
            double score = SearchOneFeature.doSearchOneFeature(requestUrl,APPID,apiSecret,apiKey,convertedFilePath,user);
            // 删除临时文件
            tempFile.delete();
            filePart.delete();
            System.out.println(score);
            Map<String, Object> resData = new HashMap<>();
            if (score > 0.5) {
                ServletBase.reqSuccess(resData);
                req.getSession().setAttribute("loginUser", user);
                Cookie c = new Cookie("name", user.getUserName());
                resp.addCookie(c);
                resData.put("loginUser", user);
            } else {
                ServletBase.reqFail(resData);
            }

            // 返回成功响应
            ServletBase.writeJsonToResp(resp, resData);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, Object> resData = new HashMap<>();
            ServletBase.reqFail(resData);
            ServletBase.writeJsonToResp(resp, resData);
        }
    }

    // 辅助方法：从 Part 对象中提取文件名
    private String getFileNameFromPart(Part part) {
        String header = part.getHeader("content-disposition");
        String[] items = header.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";  // 如果未找到，返回空字符串
    }
}